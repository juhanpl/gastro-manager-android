using GswSupplies_WebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace GswSupplies_WebAPI.Controllers
{
    [Route("api/categories")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {


        private readonly DishContext _context;

        public CategoriesController(DishContext context) 
        {
        
            _context = context;

        }

        [HttpGet]
        public async Task<IActionResult> Get()
        {

            return Ok(await _context.Categories.ToListAsync());

        }

    }
}
