package hf_master;

//TODO this code should delete all the ProgressBoolean files so they get reset 

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class HF_Master
{


    public static void main(String[] args) throws IOException, InterruptedException, ParseException, AWTException, MonitorException, URISyntaxException {


        try {
//            Process p = Runtime.getRuntime().exec("java -cp c:\\temp test");   //Runtime.getRuntime().exec("java -version");

            //java -jar Javasampleocode.jar


           //java -jar C:\Users\User\Documents\NetBeansProjects\Javasamplecode\dist\Javasamplecode.jar

            //java -jar -cp c:\Users\User\Documents\NetBeansProjects\Javasamplecodetemp\dist Javasampleocode.jar

//            Thread.sleep(10);   //might not be necessary

            boolean foundit = false;

            Process p;

            while (true) {

                if (!foundit) {
                    p = Runtime.getRuntime().exec("java -jar C:\\Users\\"+ System.getProperty("user.name")  +"\\Documents\\NetBeansProjects\\Javasamplecode\\dist\\Javasamplecode.jar");   //Runtime.getRuntime().exec("java -version");

                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String s = null;
                    System.out.println("Here is the standard output of the command:\n");
                    while ((s = stdInput.readLine()) != null)
                        System.out.println(s);

                    System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null)
                        System.out.println(s);
                }
                foundit = false;

                Thread.sleep(6_000);

                MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost("//localhost");
                List<MonitoredVm> monitoredVms = new ArrayList();
                Set<Integer> vms = monitoredHost.activeVms();
                for (Integer vm : vms)
                    monitoredVms.add(monitoredHost.getMonitoredVm(new VmIdentifier(vm.toString())));

//                int counter = 0;
                for (MonitoredVm monitoredVm : monitoredVms) {
//                    counter++;
//                    System.out.println(counter + ": " + MonitoredVmUtil.commandLine(monitoredVm));

                    if (MonitoredVmUtil.commandLine(monitoredVm).contains("Javasamplecode"))
                        foundit = true;
                }
            }
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//            String s = null;
//            System.out.println("Here is the standard output of the command:\n");
//            while ((s = stdInput.readLine()) != null)
//                System.out.println(s);
//
//            System.out.println("Here is the standard error of the command (if any):\n");
//            while ((s = stdError.readLine()) != null)
//                System.out.println(s);

//            System.exit(0);
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }


}
