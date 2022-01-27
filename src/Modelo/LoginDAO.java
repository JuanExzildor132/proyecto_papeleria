
package Modelo;
//llamado de las clases import donde viene los codigos que necesitamos
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    //se crea el llamado a la base de datos, osea a la clase donde se genero el codigo necesario para realizar esta accion
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public login log(String correo, String pass){
     //se llama  a la clase login que es donde acomodamos los get y set osea la obtencion y mostracion de datos
        login l = new login();
        //se genera la consulta en una variable para que se interpretada por el lenguaje java
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try {
            //se genera la conexion y el codigo en java para que la lea el gestor de la base de datos
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            //se cambian las variables de lenguaje sql por datos que son lanzados por el codigo en java
            ps.setString(1, correo);
            ps.setString(2, pass);
            //se ejecuta los comando y los datos que se cambiaron en el comando de sql
            rs= ps.executeQuery();
            if (rs.next()) {
                //cuando se crea la consulta, se obtienen los datos que este arrojo con el codigo de abajo donde obtiene 
                //los datos que estamos solicitando como se ve
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());//se genera un codigo que menciona si hubo error
        }
        return l;
    }
    
    public boolean Registrar(login reg){
        //para registrar un usuario se usa el siguiente codigo en sql para que lo interprete el sistema 
        //cambiando las variables por datos que el usuario ingreso en la interfaz
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarUsuarios(){
        //En esta clase se crea un listado de todos los usuarios con el siguiente codigo en SQl
       List<login> Lista = new ArrayList();
       String sql = "SELECT * FROM usuarios";
       try {
           //igualmente se crea el llamado a la base de datos
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               login lg = new login();
               lg.setId(rs.getInt("id"));
               lg.setNombre(rs.getString("nombre"));
               lg.setCorreo(rs.getString("correo"));
               lg.setRol(rs.getString("rol"));
               Lista.add(lg);
           }
           //se obtiene los datos que el comando obtuvo de la base de datos
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
}
