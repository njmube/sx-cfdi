package sx.cfdi.ine;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sx.cfdi.ine
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link INE }
     * 
     */
    public INE createINE() {
        return new INE();
    }

    /**
     * Create an instance of {@link INE.Entidad }
     * 
     */
    public INE.Entidad createINEEntidad() {
        return new INE.Entidad();
    }

    /**
     * Create an instance of {@link INE.Entidad.Contabilidad }
     * 
     */
    public INE.Entidad.Contabilidad createINEEntidadContabilidad() {
        return new INE.Entidad.Contabilidad();
    }

}
