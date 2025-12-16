using System;
using System.Collections.Generic;

namespace GswSupplies_WebAPI.Models;

public partial class Recipe
{
    public int RecipeId { get; set; }

    public int DishId { get; set; }

    public string ProcessDescription { get; set; } = null!;

    public int TimeInMinutes { get; set; }

    public virtual Dish Dish { get; set; } = null!;
}
