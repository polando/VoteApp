/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.web;


import javax.faces.FactoryFinder;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import net.bootsfaces.component.ComponentsEnum;

/**
 *
 * @author ussocom
 */

@ManagedBean(eager=true)
@ApplicationScoped
public class InitBootsfacesBean {
    /**
     * Register the Bootsfaces renderers
     */
    
      public InitBootsfacesBean() {
        initializeBootsfacesRenderers();
    }
      
      private final RenderKit defaultRenderKit = getDefaultRenderKit();
      
     private void initializeBootsfacesRenderers() {
         
         
        
        // Loop through all of the Bootsfaces components
        for (ComponentsEnum value : ComponentsEnum.values()) {
            
            // Get the component class information
            String className;
            
            // switchComponent has wrong classpath in ComponentsEnum
            if (value.name().equals("switchComponent")) {
                // Use correct qualified name
                className = "net.bootsfaces.component.switchComponent.Switch";
            } else {
                // Otherwise, use specified value
                className = value.classname();
            }
            
            // See if this is an internal reference
            if (className.contains("Internal")) {
                System.out.println("Init Bootsfaces: Skipping internal component: " + className);
            } else {
                
                System.out.println("Init Bootsfaces: Processing component: " + className);
                
                try {
                     // See if we can instantiate the class
                    Class componentClass = Class.forName(className);
                    Class<UIComponentBase> baseClass = componentClass.asSubclass(UIComponentBase.class);
                    UIComponentBase component = baseClass.newInstance();
                    String rendererFamily = component.getFamily();
                    String rendererType = component.getRendererType();

                    // Determine the renderer class name
                    String simpleName = componentClass.getSimpleName();
                    String rendererClassName;
                    switch (simpleName) {
                        case "NavCommandLink":
                            // Shares same renderer with NavLink
                            rendererClassName = "net.bootsfaces.component.navLink.NavLinkRenderer";
                            break;
                        default:
                            // We have to guess at the name of the renderer
                            rendererClassName = className + "Renderer";
                    }
                    
                    // Look up the renderer
                    Class rendererSuperclass = Class.forName(rendererClassName);
                    Class<Renderer> rendererClass = rendererSuperclass.asSubclass(Renderer.class);
                    Renderer renderer = rendererClass.newInstance();
                    System.out.println( "Init Bootsfaces: Registering renderer: " + rendererFamily + "/" + rendererType);

                    // ****** THIS IS THE IMPORTANT CALL TO REGISTER THE RENDERER *********
                    addRenderer(rendererFamily, rendererType, renderer);
                } catch (Throwable e) {
                    System.out.println( "Init Bootsfaces: Unable to register renderer for component: " + className);
                }
           }
        }
    }

    public void addRenderer(String family, String rendererType, Renderer renderer) {
        defaultRenderKit.addRenderer(family, rendererType, renderer);
    }
    
           

        public RenderKit getDefaultRenderKit() {
                RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                return renderKitFactory.getRenderKit(facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT);
        }


}
