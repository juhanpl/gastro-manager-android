using System;
using System.Collections.Generic;

namespace GswSupplies_WebAPI.Models;

public partial class DishesIngredient
{
    public int DishId { get; set; }

    public int IngredientId { get; set; }

    public int Quantity { get; set; }

    public string Unit { get; set; } = null!;

    public virtual Dish Dish { get; set; } = null!;

    public virtual Ingredient Ingredient { get; set; } = null!;
}
