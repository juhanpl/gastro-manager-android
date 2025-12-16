using System;
using System.Collections.Generic;

namespace GswSupplies_WebAPI.Models;

public partial class Dish
{
    public int DishId { get; set; }

    public string DishName { get; set; } = null!;

    public int BaseServings { get; set; }

    public int CategoryId { get; set; }

    public string ImagePath { get; set; } = null!;

    public string SourceRecipeLink { get; set; } = null!;

    public string Description { get; set; } = null!;

    public decimal FinalPriceForClients { get; set; }

    public virtual Category Category { get; set; } = null!;

    public virtual ICollection<Recipe> Recipes { get; set; } = new List<Recipe>();
}
