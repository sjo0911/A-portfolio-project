using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using HighscoreApi.bt.Services;
using HighscoreApi.Configurations;
using HighscoreApi.Controllers;
using HighscoreApi.Daos;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.OpenApi.Models;

namespace HighscoreApi
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            IMQController mQController = new MQController();
            services.AddControllers();
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "HighscoreApi", Version = "v1" });
            });
            services.Configure<HigschoreDbSettings>(
                Configuration.GetSection(nameof(HigschoreDbSettings)));
            services.AddSingleton<IHighscoreDbSettings>(sp => sp.GetRequiredService<IOptions<HigschoreDbSettings>>().Value);
            services.AddSingleton<IHighscoreMongoDAO, HighscoreMongoDAO>();
            services.AddScoped<IHighscoreService, HighscoreService>();
            services.AddSingleton<IMQController>(mQController);
            services.AddCors(options =>
            {
                    // AllowAnyOrigin will cause security issues
                    options.AddPolicy("default", policy =>
                {
                    policy.SetIsOriginAllowed((host) => true)
                        .AllowAnyHeader()
                        .AllowAnyMethod()
                        .AllowCredentials();
                });
            });  
            services.AddMvc(options =>
            {
                options.SuppressAsyncSuffixInActionNames = false;
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            app.UseCors("default");
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "HighscoreApi v1"));
            }

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
