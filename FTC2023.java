package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class FTC2023 extends LinearOpMode {
   
   DcMotorEx Elev1;
   DcMotorEx Elev2;
   TouchSensor touch;


   

   @Override
   public void runOpMode() throws InterruptedException 
   {
       
      DcMotor motor0 = hardwareMap.dcMotor.get("motor0");
      DcMotor motor1 = hardwareMap.dcMotor.get("motor1");
      DcMotor motor2 = hardwareMap.dcMotor.get("motor2");
      DcMotor motor3 = hardwareMap.dcMotor.get("motor3");
      DcMotor Elev3 = hardwareMap.dcMotor.get("leftEncoder");

      touch = hardwareMap.get(TouchSensor.class, "Touch");

      Elev1 = hardwareMap.get(DcMotorEx.class, "Elev1");
      Elev2 = hardwareMap.get(DcMotorEx.class, "Elev2");


      Servo  GripRight = hardwareMap.servo.get("GripRight");
      Servo  GripLeft = hardwareMap.servo.get("GripLeft");
      
      


      //brakes
      motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      motor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Elev1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Elev2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

      GripRight.setPosition(0.06);
      GripLeft.setPosition(0.98);
      
      Elev1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Elev2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


      waitForStart();
      if (isStopRequested()) return;
      while (opModeIsActive()) 
      {
         double y = (-gamepad1.left_stick_y/2); 
         double x = (gamepad1.left_stick_x/2) * 1.125; 
         double rx = (gamepad1.right_stick_x/2);
         
         
          if (gamepad1.left_stick_button) // stick buttons are the new accelerators
         {
            y = (-gamepad1.left_stick_y); 
            x = (gamepad1.left_stick_x) * 1.125; 
         }
         else if (gamepad1.right_stick_button)
         {
            rx = (gamepad1.right_stick_x);
         }

        
         
         
         double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
         double motor0Power = (y - x - rx) / denominator;
         double motor1Power = (y + x - rx) / denominator;
         double motor2Power = -(y - x + rx) / denominator;
         double motor3Power = -(y + x + rx) / denominator;
         if (gamepad1.dpad_left || gamepad1.x) //Dpad right and left Moves robot horizantaly on barrier 
         {
             motor0.setPower(0.8);
             motor1.setPower(-0.8);//two is reversed
             motor2.setPower(-0.8);
             motor3.setPower(0.8);
             
         }
         else if (gamepad1.dpad_right || gamepad1.b)
         {
            motor0.setPower(-0.8);
             motor1.setPower(0.8);//two is reversed
             motor2.setPower(0.8);
             motor3.setPower(-0.8);
         }
         else if (gamepad1.right_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else if (gamepad1.left_trigger>0.5&&y<0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad1.left_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power/2);
            motor1.setPower(motor1Power/2);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         else if (gamepad1.right_trigger>0.5&&y>0)
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power/2);
            motor3.setPower(motor3Power/2);
         }
         else
         {
            motor0.setPower(motor0Power);
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
         }
         
         if (gamepad1.left_bumper)
         {
            



            Elev1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            Elev1.setPower(-0.7); 
            
            
            Elev2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            Elev2.setPower(0.7);
            

         } 
         else if (gamepad1.right_bumper)
         {

            Elev1.setTargetPosition(2950);
            Elev1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Elev1.setPower(0.8); 
            
            Elev2.setTargetPosition(-2950);
            Elev2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Elev2.setPower(-0.8);
            

         }
         
         else {
             Elev1.setPower(0);
             Elev2.setPower(0);

         }
         
         if (gamepad1.x) //Dpad right and left Moves robot horizantaly on barrier 
         {
             motor0.setPower(0.5);
             motor1.setPower(-0.5);//two is reversed
             motor2.setPower(-0.5);
             motor3.setPower(0.5);
         }
         else if (gamepad1.b)
         {
             motor0.setPower(-0.5);
             motor1.setPower(0.5);//two is reversed
             motor2.setPower(0.5);
             motor3.setPower(-0.5);
         }
         // if (gamepad2.right_trigger > 0.5)
         // {
         //    Elev3.setPower(1);
         // }
         // else if (gamepad2.left_trigger > 0.5)
         // {
         //    Elev3.setPower(-1);
         // }
         // else 
         // {
         //    Elev3.setPower(0);
         // }
         

          //if (gamepad1.x)
         //{ holding x closes the grip
            // GripRight.setPosition(0.95);
            // GripLeft.setPosition(0.095);
         //}
         
         if (gamepad1.left_trigger >0.1){ 
            //holding x closes the grip
            GripRight.setPosition(0.5);
            GripLeft.setPosition(0.5);
            sleep(300);
            
         }
         else if(gamepad1.right_trigger>0.1)
         {
            GripRight.setPosition(0.06);
            GripLeft.setPosition(0.98);
         }
         
         if (touch.isPressed()) {
            
            Elev1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Elev2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            
            
            Elev1.setPower(0);
            Elev2.setPower(0);
            
            // if (gamepad1.left_bumper) {
               
            //    Elev1.setPower(0);
            //    Elev2.setPower(0);
            
               
            // }else {
               
            // }
            

         

      
         telemetry.addData("motor0", motor0.getPower());
         telemetry.addData("motor1", motor1.getPower());
         telemetry.addData("motor2", motor2.getPower());
         telemetry.addData("motor3", motor3.getPower());
         telemetry.addData("GripRight", GripRight.getPosition());
         telemetry.addData("GripLeft", GripLeft.getPosition());
         telemetry.addData("Elev1 Position", Elev1.getCurrentPosition());
         telemetry.addData("Elev2 Position", Elev2.getCurrentPosition());



         
      }
   }
}
}