namespace Repaso01.Models;

public class Producto
{
    public int Id { get; set; }
    public string Nombre { get; set; }
    public decimal Precio { get; set; }
    public int Stock { get; set; }
    public string Categoria { get; set; }
    public bool EnOferta { get; set; }

    public Producto(int id, string nombre, decimal precio, int stock, string categoria, bool enOferta)
    {
        Id = id;
        Nombre = nombre;
        Precio = precio;
        Stock = stock;
        Categoria = categoria;
        EnOferta = enOferta;
    }

    public override string ToString()
    {
        return $"{Nombre} - ${Precio} - Stock: {Stock} - {Categoria} - {(EnOferta ? "EN OFERTA" : "Precio normal")}";
    }
}
