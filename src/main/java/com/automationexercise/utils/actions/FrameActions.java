package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {


    private final WebDriver driver;
    private final WaitManager waitManager;



    public FrameActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);

    }


    /** switches to a frame by index
     */


    public void switchToFrameByIndex(int index){

        waitManager.fluentWait().until(d->

        {
            try {
                d.switchTo().frame(index);
                LogsManager.info("Switched to frame by index:"+index);
                return true;

            }
            catch (Exception e)
            {
                return  false;
            }
                }

                );

    }


    /** switches to a frame by name or id
     *
     * @param nameOrId the name or id of the frame
     */

    public void switchToFrameByName(String nameOrId){
        waitManager.fluentWait().until(d->

                {
                    try{

                        d.switchTo().frame(nameOrId);
                        LogsManager.info("Switched to frame by name or id:"+nameOrId);
                        return true;

                    }
                    catch (Exception e){
                        return false;
                    }
                }



                );
    }



    public void switchToFrameByElement(By frameLocator){


        waitManager.fluentWait().until(d->


                {

                  try {
                      d.switchTo().frame(d.findElement(frameLocator));
                      LogsManager.info("Switched to frame by element:"+frameLocator);
                      return true;
                  }
                  catch (Exception e)
                  {
                      return false;
                  }

                }

                );
    }


    public void switchToDefaultContent(){
        waitManager.fluentWait().until(d->

                {
                    try{
                        d.switchTo().defaultContent();
                        LogsManager.info("Switched to default content");
                        return true;
                    }
                    catch (Exception e)
                    {
                        return false;
                    }
                }


                );
    }


}
