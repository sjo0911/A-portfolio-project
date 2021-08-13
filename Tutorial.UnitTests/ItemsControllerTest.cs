
using System;
using System.Threading.Tasks;
using FluentAssertions;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Moq;
using Tutorial.Api.Controllers;
using Tutorial.Api.Dtos;
using Tutorial.Api.Entities;
using Tutorial.Api.Repositories;
using Xunit;

namespace Tutorial.UnitTests
{
    public class ItemsControllerTest
    {
        private readonly Mock<IItemsRepository> repositoryStub = new();
        private readonly Mock<ILogger<ItemsController>> loggerStub = new();
        private readonly Random rand = new();

        [Fact]
        public async Task GetItemAsync_WithUnexistingItem_ReturnsNotFound()
        {
            //Arange
            repositoryStub.Setup(repo => repo.GetItemAsync(It.IsAny<Guid>()))
                .ReturnsAsync((Item)null);

            var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);
            //Act
            var result = await controller.GetItemAsync(Guid.NewGuid());
            //Assert
            Assert.IsType<NotFoundResult>(result.Result);
            result.Result.Should().BeOfType<NotFoundResult>();
        }

                [Fact]
        public async Task GetItemAsync_WithExistingItem_ReturnsItem()
        {
            //Arange
            var expectedItem = CreateRandomItem();
            repositoryStub.Setup(repo => repo.GetItemAsync(It.IsAny<Guid>()))
                .ReturnsAsync(expectedItem);
            var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);
            //Act
            var result = await controller.GetItemAsync(Guid.NewGuid());
            //Assert
            result.Value.Should().BeEquivalentTo(
                expectedItem,
                options => options.ComparingByMembers<Item>());
        }

        [Fact]
        public async Task GetItemsAsync_WithExistingItems_ReturnsItems()
        {
            //Arrange

            var expectedItems = new[]{CreateRandomItem(),CreateRandomItem(),CreateRandomItem(),CreateRandomItem()};
            repositoryStub.Setup(repo => repo.GetItemsAsync())
                .ReturnsAsync(expectedItems);
            var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);

            //Act
            
            var result = await controller.GetItemsAsync();

            //Assert
            result.Should().BeEquivalentTo(
                expectedItems,
                option => option.ComparingByMembers<Item>()
            );
        }

        private Item CreateRandomItem()
        {
                return new()
                {
                    Id = Guid.NewGuid(),
                    Name = Guid.NewGuid().ToString(),
                    Price = rand.Next(0, 1000),
                    CreatedDate = DateTimeOffset.UtcNow
                };
        }
    }
}
