using System;
using System.Collections.Generic;

namespace GswSupplies_WebAPI.Models;

public partial class Ingredient
{
    public int IngredientId { get; set; }

    public string IngredientName { get; set; } = null!;

    public decimal PricePerUnit { get; set; }

    public string MainUnit { get; set; } = null!;

    public int AvailableCountInStock { get; set; }
}
