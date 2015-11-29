package at.abraxas.amarino.message;

/**
 * Created by zysd on 15/11/22.
 */
public class ControlMessage {


    /*
            // Car Direction Const Definition
            #define TurnOff   0
            #define FORWARD   1
            #define BACKWARD  2
            #define TurnLeft  3
            #define TurnRight 4
            #define LeftRotate 5
            #define RightRotate 6
            #define MelodySongPlay  7
            #define PIDSwitchON     8       // PID function switch
            #define PIDSwitchOFF    9       // PID function switch
            #define Left_Right_PowerDrop 32 // drop power 64 while turn left/right

     */

    public static final char directionFlag      =   'c';  //控制小车的方向


    public static final int TurnOff              =   0;
    public static final int FORWARD              =   1;
    public static final int BACKWARD             =   2;
    public static final int TurnLeft             =   3;
    public static final int TurnRight            =   4;
    public static final int LeftRotate           =   5;
    public static final int RightRotate          =   6;
    public static final int MelodySongPlay       =   7;
    public static final int PIDSwitchON          =   8;
    public static final int PIDSwitchOFF         =   9;
    public static final int Left_Right_PowerDrop =  32;



}
