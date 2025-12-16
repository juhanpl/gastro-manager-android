using GswSupplies_WebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace GswSupplies_WebAPI.Controllers
{
    [Route("api/dishes")]
    [ApiController]
    public class DishController : ControllerBase
    {

        private readonly DishContext _context;

        public DishController(DishContext context) 
        {

            _context = context;

        }

        [HttpGet]
        public async Task<IActionResult> Get()
        {

            return Ok(await _context.AllDishes.ToListAsync());

        }

    }
}
