package main.Java;

import com.intellij.notification.impl.NotificationActionProvider;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.graph.option.IconOptionItem;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Marek Bruchatý on 16/03/16.
 */
public class PluginRegistration implements ApplicationComponent{

    @NotNull
    @Override
    public String getComponentName() {
        return "UTDDPlugin";
    }

    @Override
    public void initComponent() {
//        ActionManager am = ActionManager.getInstance();
//        //TestPairAction tpa = new TestPairAction(IconLoader.getIcon("UTDDPlugin/resources/icons/tdd.png"));
//        TestPairAction tpa = new TestPairAction("blabla");
//        am.registerAction("TestPairAction", tpa);
//
//        DefaultActionGroup toolBar = (DefaultActionGroup) am.getAction("MainToolBar");
//
//        toolBar.addSeparator();
//        toolBar.add(tpa);



    }

    @Override
    public void disposeComponent() {

    }
}
