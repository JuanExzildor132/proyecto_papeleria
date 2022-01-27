package Modelo;
/*
Aqui se importan todos los packs que se necesitan
para ir llamando las clases, como los comandos importantes de 
la base de datos
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author JuanExzildor
 */
public class ClienteDao {
    //se manda a llamar la clase conexion que es la que realiza el llamado entre el programa y la base de datos
    Conexion cn = new Conexion();
    Connection con;
    //Se ve como se manda a llamar la clase donde se realiza la conexion
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarCliente(Cliente cl){
        //se crea el ingreso o registro de los nuevos datos en la base de datos como podemos ver asi se genera la consulta
        String sql = "INSERT INTO clientes (dni, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            //Aqui se realiza primero la conexion
             con = cn.getConnection();
            //Aqui se crea la variable de conectar con el comando sql que generamos anterior mente
            ps = con.prepareStatement(sql);
            //se cambian todas las variables en leguaje sql a java para que se ingresen los datos
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.execute();
            //se anexan todas las variables y se ejecuta el comando en sql
            return true;
            //se devuelve el valor si es verdad se generan las siguientes opciones
        } catch (SQLException e) {
            //si nuestro valor fue exitoso se mandara un mensaje en pantalla donde nos este mostrando que se agrego correctamente
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            //de caso contrario si no se conecto la variable o hubo errores mandara un mensaje de error en pantalla
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    //en la siguiente clase creada sirve para que podamos ver todos los datos que tenga la tabla clientes
    //como tambien mostrarnos todos sus datos que se le aya creado
   public List ListarCliente(){
       List<Cliente> ListaCl = new ArrayList();
       String sql = "SELECT * FROM clientes";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Cliente cl = new Cliente();
               cl.setId(rs.getInt("id"));
               cl.setDni(rs.getString("dni"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               ListaCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaCl;
       //como podemos a qui se regresa la clase de los datos con la informacion que se aya obtenido
   }
   //Clase donde se elimina un cliente, igualmente los codigos son para que entre a la base de datos
   //y busque la tabla genere el comando
   //y regrese el valor que se aya generado
   public boolean EliminarCliente(int id){
       String sql = "DELETE FROM clientes WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
   //Clase donde se modifica al cliente 
   //ingresa a la base de datos 
   //y return el valor de la modificacion que ayamos creado
   public boolean ModificarCliente(Cliente cl){
       String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);   
           ps.setString(1, cl.getDni());
           ps.setString(2, cl.getNombre());
           ps.setString(3, cl.getTelefono());
           ps.setString(4, cl.getDireccion());
           ps.setInt(5, cl.getId());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
   //una clase especial ya que busca un dato en si que deseamos 
   //en nuestra base de datos
   //y esta se conecte a la base de datos busque el registro y return con el valor
   public Cliente Buscarcliente(int dni){
       Cliente cl = new Cliente();
       String sql = "SELECT * FROM clientes WHERE dni = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, dni);
           rs = ps.executeQuery();
           if (rs.next()) {
               cl.setId(rs.getInt("id"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return cl;
   }
   
}
