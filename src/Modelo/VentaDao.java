
package Modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

public class VentaDao {
    //se crea la conexion en la base de datos
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public int IdVenta(){
        int id = 0;
        //comando donde se selecciona un valor maximo que hay en la tabla ventas
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            //aqui obtiene ese dato y lo registra en la variable que se establecio para mostrar o llamarla
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    //clase donde se registra la venta que se crea en la interfaz grafica
    public int RegistrarVenta(Venta v){
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
          //conexion y cambio de datos por la consulta en sql de arriba
            ps.setInt(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            //se cambian todos los datos de la consulta por datos que se obtengan en nuestro codigo de java
            ps.execute();
            //se crea la ejecucion del comando de arriba con datos de la interfaz
        } catch (SQLException e) {
            System.out.println(e.toString());
            //se muestra que la conexion fue existosa
        }finally{
            try {
                //se cierra la conexion si no 
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    //la clase donde se registran la venta, pero con el cambio de que esta va dirigida 
    //al reporte de registros 
    public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
       //almacena datos de que venta se cambiaron ademas de la cantidad de productos y precios, que corresponde a la tabla producto 
       try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            //conexion y cambio de la consulta
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            //se agregan los nuevos datos
            ps.execute();
            //se ejecuta el comando
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    //esta clase es importante por que es donde se cambian la cantidad de producto cuando se este generando una venta 
    //ya que elimina 
    //el numero de stock q este en la tabla producto
    public boolean ActualizarStock(int cant, int id){
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    //clase donde se alista las ventas 
    //que tengamos o se ayan realizado si no no mostrara nada
    public List Listarventas(){
       List<Venta> ListaVenta = new ArrayList();
       String sql = "SELECT c.id AS id_cli, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Venta vent = new Venta();
               vent.setId(rs.getInt("id"));
               vent.setNombre_cli(rs.getString("nombre"));
               vent.setVendedor(rs.getString("vendedor"));
               vent.setTotal(rs.getDouble("total"));
               ListaVenta.add(vent);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaVenta;
   }
    //clase donde se busca una venta que el usuario desea conocer 
    public Venta BuscarVenta(int id){
        Venta cl = new Venta();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        //comando en sql para generar la busqueda por su id
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                //datos que necesitamos mostrar y que se obturion con la busqueda
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setVendedor(rs.getString("vendedor"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
    //clase para generar un archivo en pdf
    //una alternativa para cambiar el jasperreport
    public void pdfV(int idventa, int Cliente, double total, String usuario) {
        try {
            //se usa la clase Dated donde estael arhivo
            Date date = new Date();
            FileOutputStream archivo;
            //crea un fichero, salvo que exista y sea de sólo lectura. 
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            //se crea donde estaran almacenados el archivo
            File salida = new File(url + File.separator + "venta.pdf");
            //es donde se guardar el achivo con el nombre que se obtendra
            archivo = new FileOutputStream(salida);
            //se llama el archivo que vimos antes con los datos que se le otorgaron
            Document doc = new Document();
            /// Se asocia el documento al OutputStream 
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            //abre el documento que se creo, para ingresar lo siguiente
            Image img = Image.getInstance(getClass().getResource("/Img/logo_pdf.png"));//se agrega una imagen que tengamos aguarda
            //en nuestro proyecto
            //Fecha
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);//se agrega el tiempo que marque el equipo
            //ademas de colocar el tipo de letra, como su color por defecto
            fecha.add(Chunk.NEWLINE);
            //comando para agregar una nueva linea a nuestro archivo
            fecha.add("Vendedor: " + usuario + "\nFolio: " + idventa + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");
            //parte donde agregamos los datos, a nuestro archivo como tambien la fecha, el usuario, un texto como vendedor
            PdfPTable Encabezado = new PdfPTable(4);
            //comando para agregar un titulo o datos a nuestra cabeza de archivo pero renombrando con lo siguiente
            Encabezado.setWidthPercentage(100);
            //el tamaño del porcentaje que exista
            Encabezado.getDefaultCell().setBorder(0);
            //el borde de datos 
            float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
            //el tamaño del encabezado
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //aqui vemos como acomados los datos que queremos que se muestren en la parte superior del documento
            //info empresa
            String config = "SELECT * FROM config";
            //se almacenan los datos de la consulta en config para almacenar datos
            String mensaje = "";
            //se crea una variable no vacia donde se  podra agregar los datos
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                //conexion a la base de datos
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") + "\nNombre: " + rs.getString("nombre") + "\nTeléfono: " + rs.getString("telefono") + "\nDirección: " + rs.getString("direccion") + "\n\n");
                //ingresando datos de nuestra base de datos al encabezado con datos de la tabla que colocamos como detalle
                //donde vienen datos de la empresa
                }
                //se agregan datos a la variable mensaje con los datos que tiene la misma variable con la que existe en la base de datos
                //como tambien los datos necesarios para que se muestre los datos de la empresa 
                //en el archivo 
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            //cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);
//en esta parte se agrega un nuevo formato donde llevaran todos los datos de nuestro cliente tanto se alla registrado en la base de datos
            PdfPTable proveedor = new PdfPTable(3);
            proveedor.setWidthPercentage(100);
            proveedor.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{50f, 25f, 25f};
            proveedor.setWidths(columnWidthsCliente);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            //aqui es el codigo de la posicion que tendra nuestro formato en el archivo pdf, como se acodara la informacion
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            //se realiza el llamdo para agregar el contenido de la informacion ademas de ponerle en negrita como se muestra
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);
            //Se muestra si tendra borde los siguiente datos
            proveedor.addCell(cliNom);
            proveedor.addCell(cliTel);
            proveedor.addCell(cliDir);
            //se almacena la informacion que obtuvimos con anterioridad
            String prove = "SELECT * FROM clientes WHERE id = ?";
            //se crea la consulta, pero con que datos necesitamos mostrar
            try {
                //aqui se realiza el llamado a la base datos pero con la tabla clientes
                ps = con.prepareStatement(prove);
                ps.setInt(1, Cliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    //se obtienen el cambio del registro con los datos que se solicitan, y se almacenan en addCell
                    proveedor.addCell(rs.getString("nombre"));
                    proveedor.addCell(rs.getString("telefono"));
                    proveedor.addCell(rs.getString("direccion") + "\n\n");
                } else {
                    //aqui si de caso contrario no existe
                    //se cambiara por los siguientes datos
                    proveedor.addCell("Publico en General");
                    proveedor.addCell("S/N");
                    proveedor.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(proveedor);

            PdfPTable tabla = new PdfPTable(4);
            //se realiza el llamado a la clase tabla
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            //codigo necesario para acomadara la posicion logintud de los datos que se mostraran en el archivo pdf
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            //se almacenan los datos que estemos solicitando 
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            //se cambian el borde los datos d enuestra tabla, quitamos que no muestre bordes 
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            //como tambien el color que se muestre en el fondooo
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            //se cambia el valor en la tablea
            String product = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
            try {
                //se realiza la conexion con la base de datos
                ps = con.prepareStatement(product);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                //se busca el archivo
                while (rs.next()) {
                    //se cambian los valores de la tabla de arriba con datos de la base de datos
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            //se agrega la parte final de nuestro archivo
            Paragraph info = new Paragraph();
            //se manda a llamar
            info.add(Chunk.NEWLINE);
            //comando para crear una interlinea en el documento
            info.add("Total S/: " + total);
            //se agrega un texto mas un dato de una tabla
            info.setAlignment(Element.ALIGN_RIGHT);
            //se coloca la posicion del elemento
            doc.add(info);
            //se almacena la informacion
            Paragraph firma = new Paragraph();
            //se crea una nueva variable para agregar al texto del pdf
            firma.add(Chunk.NEWLINE);
        //se crea un enter en el archivo
            firma.add("Cancelacion \n\n");
            //se agrega texto que muestra mas dos interlineas 
            firma.add("------------------------------------\n");
            //se agrega el texto o linea de arriba con un enter
            firma.add("Firma \n");
            //se agrega el texto mas un enter
            firma.setAlignment(Element.ALIGN_CENTER);
            //se agrega la posicion del texto 
            doc.add(firma);
            //se almacenan los datos en la variable firma
            Paragraph gr = new Paragraph();
            //se realiza una nueva variable en la clase pargraph
            gr.add(Chunk.NEWLINE);
            gr.add(mensaje);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    
}
