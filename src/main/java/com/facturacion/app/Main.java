package com.facturacion.app;

import com.facturacion.model.Articulo;
import com.facturacion.model.AuditoriaApp;
import com.facturacion.model.FacturaVenta;
import com.facturacion.model.FacturaVentaDetalle;
import com.facturacion.model.ListaPrecio;
import com.facturacion.model.ListaPrecioArticulo;
import com.facturacion.model.Marca;
import com.facturacion.model.PuntoVenta;
import com.facturacion.model.Rubro;
import com.facturacion.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Date ahora = new Date();

            Usuario usuario = new Usuario("admin", "1234", "Administrador", "Sistema");

            PuntoVenta puntoVenta = new PuntoVenta();
            puntoVenta.setNumero(1);
            puntoVenta.setDescripcion("Casa Central");
            puntoVenta.setTipoEmision("Electronica");
            puntoVenta.setDomicilioComercial("Mendoza");
            configurarAuditoria(puntoVenta, usuario, ahora);

            Marca marca = new Marca();
            marca.setCodigo(1);
            marca.setDenominacion("Generica");
            configurarAuditoria(marca, usuario, ahora);

            Rubro rubro = new Rubro();
            rubro.setCodigo(10);
            rubro.setDenominacion("Insumos");
            configurarAuditoria(rubro, usuario, ahora);

            Articulo articulo = new Articulo();
            articulo.setCodigo("ART-001");
            articulo.setDenominacion("Producto de prueba");
            articulo.setMarca(marca);
            articulo.setRubro(rubro);
            configurarAuditoria(articulo, usuario, ahora);

            ListaPrecio listaPrecio = new ListaPrecio();
            listaPrecio.setCodigo("LP-001");
            listaPrecio.setDenominacion("Lista general");
            configurarAuditoria(listaPrecio, usuario, ahora);

            ListaPrecioArticulo listaPrecioArticulo = new ListaPrecioArticulo();
            listaPrecioArticulo.setListaPrecio(listaPrecio);
            listaPrecioArticulo.setArticulo(articulo);
            listaPrecioArticulo.setPrecioVenta(1500.00);
            configurarAuditoria(listaPrecioArticulo, usuario, ahora);

            FacturaVentaDetalle detalle1 = new FacturaVentaDetalle();
            detalle1.setListaPrecioArticulo(listaPrecioArticulo);
            detalle1.setDescripcion("Producto de prueba");
            detalle1.setCantidad(2);
            detalle1.setPrecioUnitario(1500.00);
            detalle1.setPorcentajeBonificacion(0);
            detalle1.setImporteNeto(3000.00);
            detalle1.setImporteIva(630.00);
            detalle1.setImporteSubtotal(3630.00);

            FacturaVentaDetalle detalle2 = new FacturaVentaDetalle();
            detalle2.setListaPrecioArticulo(listaPrecioArticulo);
            detalle2.setDescripcion("Producto de prueba - segunda linea");
            detalle2.setCantidad(1);
            detalle2.setPrecioUnitario(1500.00);
            detalle2.setPorcentajeBonificacion(10);
            detalle2.setImporteNeto(1350.00);
            detalle2.setImporteIva(283.50);
            detalle2.setImporteSubtotal(1633.50);

            FacturaVenta facturaVenta = new FacturaVenta();
            facturaVenta.setNumero(1L);
            facturaVenta.setFechaEmision(ahora);
            facturaVenta.setPuntoVenta(puntoVenta);
            facturaVenta.setImporteCobrado(5263.50);
            facturaVenta.setImporteSaldo(0);
            facturaVenta.setImporteTotal(5263.50);
            facturaVenta.setEstado("EMITIDA");
            facturaVenta.setObservaciones("Factura generada desde el TP de JPA/Hibernate");
            configurarAuditoria(facturaVenta, usuario, ahora);

            facturaVenta.agregarDetalle(detalle1);
            facturaVenta.agregarDetalle(detalle2);

            // Requisito del TP: un unico llamado a persist sobre la cabecera.
            em.persist(facturaVenta);

            em.getTransaction().commit();

            System.out.println("Factura persistida correctamente. ID: " + facturaVenta.getId());
            System.out.println("Detalles persistidos por cascada: " + facturaVenta.getDetalles().size());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void configurarAuditoria(AuditoriaApp entidad, Usuario usuario, Date fecha) {
        entidad.setFechaAlta(fecha);
        entidad.setFechaModificacion(fecha);
        entidad.setUsuarioCarga(usuario);
        entidad.setUsuarioModificacion(usuario);
    }
}
