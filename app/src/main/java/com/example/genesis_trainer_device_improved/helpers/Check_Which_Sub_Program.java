package com.example.genesis_trainer_device_improved.helpers;


import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_EASY;
import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_HARD;
import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_MEDIUM;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_ANTI_CELULITE;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_BASIC_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_BASIC_2;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_CONTINUOUS_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_CONTINUOUS_2;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_COOLDOWN_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_COOLDOWN_2;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_ENDURANCE_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_ENDURANCE_2;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_ENDURANCE_3;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_FATBURNING_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_FATBURNING_2;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_GET_STARTED;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_MASAGE_ORDINARY;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_STRENGTH_1;
//import static com.awaresiel.genesistrainerdevice.Helpers.Constants.SUBPROGRAM_STRENGTH_2;

public class Check_Which_Sub_Program {

//  public static String timeofStimulation_ms="0";
  private static int timeofStimulation_ms=0;
    private static int restTme_ms= 0;

   /*
    * rise and fall of pulse Ci parameter and Cj paramter
    */
   private static String intensityhOfTraining="";
   private static int timeOfTraining=0;
    private static int increase_Ci=0;
    private static int decrease_Cj=0;
    private static int typeOfImpulse_DuringStimulationCb=0;
    private static int Cf =0;
    private static int Px =0;
    private static int Py =0;

  // public static boolean smoothness=false;


   static public void Check_SubProgram(String name)
    {

//        intensityhOfTraining=intensity;
//        setIntensityhOfTraining(intensity);


     if(name.contains(Constants.SUBPROGRAM_GET_STARTED ))
        {
            timeOfTraining=10;
            timeofStimulation_ms = 3000;
            restTme_ms=4000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }

        else if(name.contains(Constants.SUBPROGRAM_BASIC_1))
        {
            timeOfTraining=15;
            timeofStimulation_ms = 3000;
            restTme_ms=3000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_BASIC_2))
        {
            timeOfTraining=20;
            timeofStimulation_ms = 4000;
            restTme_ms=2000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_CONTINUOUS_1))
        {
            timeOfTraining=20;
            timeofStimulation_ms = 1000;
            restTme_ms=0;
            increase_Ci=0;
            decrease_Cj=0;
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_CONTINUOUS_2))
        {
            timeOfTraining=30;
            timeofStimulation_ms = 1000;
            restTme_ms=0;
            increase_Ci=0;
            decrease_Cj=0;
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_ENDURANCE_1))
        {
            timeOfTraining=15;
            timeofStimulation_ms = 4000;
            restTme_ms=4000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=0;

            Px =175;
            Py =250;
        }
        else if(name.contains(Constants.SUBPROGRAM_ENDURANCE_2))
        {
            timeOfTraining=20;
            timeofStimulation_ms = 4000;
            restTme_ms=2000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=0;

            Px =175;
            Py =250;
        }
        else if(name.contains(Constants.SUBPROGRAM_ENDURANCE_3))
        {
            timeOfTraining=25;
            timeofStimulation_ms = 5000;
            restTme_ms=2000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=0;

            Px =175;
            Py =250;
        }
        else if(name.contains(Constants.SUBPROGRAM_STRENGTH_1))
        {
            timeOfTraining=15;
            timeofStimulation_ms = 3000;
            restTme_ms=3000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_STRENGTH_2))
        {
            timeOfTraining=30;
            timeofStimulation_ms = 5000;
            restTme_ms=2000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=6;
            Cf=0;

            Px =175;
            Py =125;
        }
        else if(name.contains(Constants.SUBPROGRAM_FATBURNING_1))
        {
            timeOfTraining=15;
            timeofStimulation_ms = 4000;
            restTme_ms=4000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=1000;

            Px =175;
            Py =250;
        }
        else if(name.contains(Constants.SUBPROGRAM_FATBURNING_2))
        {
            timeOfTraining=25;
            timeofStimulation_ms = 5000;
            restTme_ms=3000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=1000;

            Px =175;
            Py =250;
        }
        else if(name.contains(Constants.SUBPROGRAM_COOLDOWN_1))
        {
            timeOfTraining=5;
            timeofStimulation_ms = 1000;
            restTme_ms=1000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=0;

            Px =175;
            Py =100;
        }
     else if(name.contains(Constants.SUBPROGRAM_COOLDOWN_2))
     {
         timeOfTraining=5;
         timeofStimulation_ms = 1000;
         restTme_ms=2000;
//         setIntensityhOfTraining(intensity);
         typeOfImpulse_DuringStimulationCb=0;
         Cf=1500;

         Px =175;
         Py =100;
     }
        else if(name.contains(Constants.SUBPROGRAM_MASAGE_ORDINARY))
        {
            timeOfTraining=15;
            timeofStimulation_ms = 1000;
            restTme_ms=4000;
//            setIntensityhOfTraining(intensity);
            typeOfImpulse_DuringStimulationCb=0;
            Cf=1500;
            Px =175;
            Py =100;
        }
        else if(name.contains(Constants.SUBPROGRAM_ANTI_CELULITE))
        {
            timeOfTraining=20;
            timeofStimulation_ms = 1000;
            restTme_ms=0;
            increase_Ci=0;
            decrease_Cj=0;
            typeOfImpulse_DuringStimulationCb=0;
            Cf=0;

            Px =175;
            Py =1000;

        }
    }

    public static void setIntensityhOfTraining(String intensity)
    {
        switch (intensity){
            case INTENSITY_EASY:
                increase_Ci=3500;
                decrease_Cj=7000;
                intensityhOfTraining = INTENSITY_EASY;
                break;
            case INTENSITY_MEDIUM:
                increase_Ci=3000;
                decrease_Cj=4500;
                intensityhOfTraining = INTENSITY_MEDIUM;
                break;
            case INTENSITY_HARD:
                increase_Ci=2500;
                decrease_Cj=3000;
                intensityhOfTraining = INTENSITY_HARD;
                break;
        }
    }

    public static int getTimeofStimulation_ms() {
        return timeofStimulation_ms;
    }

    public static int getRestTme_ms() {
        return restTme_ms;
    }

    public static String getIntensityhOfTraining() {
        return intensityhOfTraining;
    }

    public static int getIncrease_Ci() {
        return increase_Ci;
    }

    public static int getDecrease_Cj() {
        return decrease_Cj;
    }

    public static int getCf() {
        return Cf;
    }

    public static int getTypeOfImpulse_DuringStimulationCb() {
        return typeOfImpulse_DuringStimulationCb;
    }

    public static int getPx() {
        return Px;
    }

    public static int getPy() {
        return Py;
    }

    public static int getTimeOfTraining() {
        return timeOfTraining;
    }
}
