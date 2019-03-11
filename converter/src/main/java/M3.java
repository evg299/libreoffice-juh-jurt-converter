import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import static com.sun.star.lib.util.NativeLibraryLoader.LO_PATH_KEY;

public class M3 {
    public static void main(String[] args) {
        System.setProperty(LO_PATH_KEY, "/usr/lib/libreoffice/program/");

        try {
            // __ Get the remote office component context
            XComponentContext xContext = Bootstrap.bootstrap();
            // __ Get the remote office service manager
            XMultiComponentFactory xMCF = xContext.getServiceManager();
            // __ Get the root frame (i.e. desktop) of openoffice framework.
            Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            // __ Desktop has 3 interfaces. The XComponentLoader interface provides ability to load components.
            XComponentLoader xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);

            XDesktop xDesktop = UnoRuntime.queryInterface(XDesktop.class, oDesktop);


            // Loading the wanted document
            PropertyValue[] propertyValues = new PropertyValue[1];
            propertyValues[0] = new PropertyValue();
            propertyValues[0].Name = "Hidden";
            propertyValues[0].Value = Boolean.TRUE;

            XComponent oDocToStore = xCLoader.loadComponentFromURL("file:////home/storage/DEV/Java3/___/Untitled1.docx", "_blank", 0, propertyValues);

            XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, oDocToStore);

            // Preparing properties for converting the document
            propertyValues = new PropertyValue[2];
            propertyValues[0] = new PropertyValue();
            propertyValues[0].Name = "Overwrite";
            propertyValues[0].Value = Boolean.TRUE;

            propertyValues[1] = new PropertyValue();
            propertyValues[1].Name = "FilterName";
            propertyValues[1].Value = "writer_pdf_Export";

            xStorable.storeAsURL("file:////home/storage/DEV/Java3/___/Untitled1.docx.pdf", propertyValues);


            xDesktop.terminate();

        } catch (BootstrapException be) {
            be.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
