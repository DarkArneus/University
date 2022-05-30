%%
clear all;
%Data
L=200;          %window lenght
N=600;          %number of gathered values
i=1;
Po=100;          %surface atmospheric pressure (kPa)
Sa=10;           %(kPa/m)
S=0.2e-3;        %Smpx
G=157.7;         %Gina
dv_high=0.9;     %Vsat
dv_low=0.9;      %Vsat
Vup= 5-dv_high;
Vlow= 0+dv_low;  %as seen in the datasheet
maxval=0;        %maximum depth
minval=3;        %minimum depth
Ts=1/5;          %Arduino delay
 
v=zeros(1,L);  %vector of L samples of value equal to 0
t=0:Ts:Ts*(L-1);    %time vector
%%
h=figure('Name', 'ROUV Depth on Real Time');
plot(t, v);
title('ROUV Depth on Real Time'); xlabel('t [s]'); ylabel('Depth [m]');
grid on;
%%
% Connection with the port and deleting possible interferetions.
delete(instrfind({'Port'},{'COM4'}));
s=serial('COM4', 'BaudRate',9600, 'Terminator', 'CR/LF');
fopen(s);                                                       %open serial port 
file=fopen('resultats_depth.txt', 'w');                         %create a file to store the results
 
%%
fprintf(s,'%s\r\n' ,'S'); %Send S to start  
%%
while(i<=N+1)
    Input = fscanf(s,'%c\r\n',[1,3]);                   %read the value in format character
    val_input=str2double(Input);                        %convert into double
    depth = (((val_input*5)/(1023*G*S))-100)*(1/Sa);    %Find depth value from
                                                           
    fprintf(file, '%.1f\t %.2f\r\n', i*Ts, depth);      %write in the file the time and data sent by Arduino
    
    Prel=Sa*depth;                                      %calculate relative pression
    Pabs=Po+Prel;                                       %calculate absolute pression
    V2=G*Pabs*S;                                        
    
    
    %if we reach the saturation voltage we will set the depth as the limit value
    if(V2>Vup)                                          % then, v2=vup and depth is 3m
        depth=3;
    end
    if(V2<Vlow)% then v2=vlow and...
        depth=0;
    end
 
    %if (L samples != 0) we have to move the
    %axis
    if(i>L)
        v(L+1)= depth;                             
        v=v(2:L+1);                                                      
        t(L+1)=t(L)+Ts;                                
        t=t(2:L+1);   
    else
        v(i)=depth;
        if(depth<0)
            v(i)=0;
        end
    end
    
    % store the maximum an minimum values
    if(depth>maxval)
        maxval=depth;
    end
    if (depth<minval)
        minval=depth;
    end
    
    %plot actual depht
    figure(h);
    plot(t,v) 
    pause(Ts);                                         
 i=i+1;   
end

% write on the file the maximum an the minimum values of depth obtained
fprintf(file,'Maximum value of depth: %.2f m\n\r',maxval);
fprintf(file,'Minimum value of depth: %.2f m\n\r',minval);

fclose(file);
fclose(s);