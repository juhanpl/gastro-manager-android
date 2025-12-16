using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace GswSupplies_WebAPI.Models;

public partial class DishContext : DbContext
{
    public DishContext()
    {
    }

    public DishContext(DbContextOptions<DishContext> options)
        : base(options)
    {
    }

    public virtual DbSet<AllDish> AllDishes { get; set; }

    public virtual DbSet<Category> Categories { get; set; }

    public virtual DbSet<Dish> Dishes { get; set; }

    public virtual DbSet<DishesIngredient> DishesIngredients { get; set; }

    public virtual DbSet<Ingredient> Ingredients { get; set; }

    public virtual DbSet<Recipe> Recipes { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<AllDish>(entity =>
        {
            entity
                .HasNoKey()
                .ToView("AllDishes");

            entity.Property(e => e.Category)
                .HasMaxLength(50)
                .IsUnicode(false);
            entity.Property(e => e.DishId).HasColumnName("Dish_Id");
            entity.Property(e => e.DishName)
                .HasMaxLength(50)
                .IsUnicode(false)
                .HasColumnName("Dish_Name");
            entity.Property(e => e.FinalPriceForClients)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("Final_Price_for_Clients");
            entity.Property(e => e.ImagePath)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("Image_Path");
        });

        modelBuilder.Entity<Category>(entity =>
        {
            entity.Property(e => e.CategoryId).HasColumnName("Category_Id");
            entity.Property(e => e.CategoryName)
                .HasMaxLength(50)
                .IsUnicode(false)
                .HasColumnName("Category_Name");
        });

        modelBuilder.Entity<Dish>(entity =>
        {
            entity.Property(e => e.DishId).HasColumnName("Dish_Id");
            entity.Property(e => e.BaseServings).HasColumnName("Base_Servings");
            entity.Property(e => e.CategoryId).HasColumnName("Category_Id");
            entity.Property(e => e.Description)
                .HasMaxLength(500)
                .IsUnicode(false);
            entity.Property(e => e.DishName)
                .HasMaxLength(50)
                .IsUnicode(false)
                .HasColumnName("Dish_Name");
            entity.Property(e => e.FinalPriceForClients)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("Final_Price_for_Clients");
            entity.Property(e => e.ImagePath)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("Image_Path");
            entity.Property(e => e.SourceRecipeLink)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("Source_Recipe_Link");

            entity.HasOne(d => d.Category).WithMany(p => p.Dishes)
                .HasForeignKey(d => d.CategoryId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Dishes_Categories");
        });

        modelBuilder.Entity<DishesIngredient>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("Dishes_Ingredients");

            entity.Property(e => e.DishId).HasColumnName("Dish_Id");
            entity.Property(e => e.IngredientId).HasColumnName("Ingredient_Id");
            entity.Property(e => e.Unit)
                .HasMaxLength(20)
                .IsUnicode(false);

            entity.HasOne(d => d.Dish).WithMany()
                .HasForeignKey(d => d.DishId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Dishes_Id");

            entity.HasOne(d => d.Ingredient).WithMany()
                .HasForeignKey(d => d.IngredientId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Ingredients_Id");
        });

        modelBuilder.Entity<Ingredient>(entity =>
        {
            entity.Property(e => e.IngredientId).HasColumnName("Ingredient_Id");
            entity.Property(e => e.AvailableCountInStock).HasColumnName("Available_count_in_stock");
            entity.Property(e => e.IngredientName)
                .HasMaxLength(50)
                .IsUnicode(false)
                .HasColumnName("Ingredient_Name");
            entity.Property(e => e.MainUnit)
                .HasMaxLength(20)
                .IsUnicode(false)
                .HasColumnName("Main_Unit");
            entity.Property(e => e.PricePerUnit)
                .HasColumnType("decimal(18, 2)")
                .HasColumnName("Price_per_Unit");
        });

        modelBuilder.Entity<Recipe>(entity =>
        {
            entity.Property(e => e.RecipeId).HasColumnName("Recipe_Id");
            entity.Property(e => e.DishId).HasColumnName("Dish_Id");
            entity.Property(e => e.ProcessDescription)
                .HasMaxLength(200)
                .IsUnicode(false)
                .HasColumnName("Process_Description");
            entity.Property(e => e.TimeInMinutes).HasColumnName("Time_In_Minutes");

            entity.HasOne(d => d.Dish).WithMany(p => p.Recipes)
                .HasForeignKey(d => d.DishId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Recipes_Dishes");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
