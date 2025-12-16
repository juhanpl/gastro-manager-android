using System;
using System.Collections.Generic;

namespace GswSupplies_WebAPI.Models;

public partial class AllDish
{
    public int DishId { get; set; }

    public string DishName { get; set; } = null!;

    public string Category { get; set; } = null!;

    public string ImagePath { get; set; } = null!;

    public decimal FinalPriceForClients { get; set; }
}
