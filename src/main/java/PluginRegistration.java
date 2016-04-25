package main.java;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Author: Marek Bruchatý
 * Date: 16/03/16.
 */

public class PluginRegistration implements ApplicationComponent{

    @NotNull
    @Override
    public String getComponentName() {
        return "UTDDPlugin";
    }

    @Override
    public void initComponent() {}

    @Override
    public void disposeComponent() {}
}
