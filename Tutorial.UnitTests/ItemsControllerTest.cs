
using System;
using System.ComponentModel;
using System.Net.NetworkInformation;
using System.Reflection;
using System.Runtime.InteropServices;
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

        [Fact]
        public async Task CreateItemAsync_WithItemToCreate_ReturnsItem()
        {
        //Given
            var itemToCreate = new CreateItemDto()
            {
                Name = Guid.NewGuid().ToString(),
                Price = rand.Next(1000)
            };
            var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);
        //When
        var result = await controller.CreateItemAsync(itemToCreate);

        //Then
        var createdItem = (result.Result as CreatedAtActionResult).Value as ItemDto;
        createdItem.Should().BeEquivalentTo(
            itemsToCreate,
            OperationalStatus => OperationalStatus.ComparingByMembers<ItemDto>().ExcludingMissingMembers()
        );
        createdItem.Id.Should().NotBeEmpty();
        createdItem.CreatedDate.Should().BeCloseTo(DateTimeOffset.UtcNow, 1000);
        }

        [Fact]
        public async Task UpdateItemAsync_WithExistingItem_ReturnsNoContent()
        {
        //Given
        var existingItem = CreateRandomItem();
        repositoryStub.Setup(repo => repo.GetItemAsync(It.IsAny<Guid>()))
                .ReturnsAsync(expectedItem);

        var itemId = existingItem.Id;
        var itemToUpdate = new UpdateItemDto()
        {
            Name = Guid.NewGuid().ToString(),
            Price = existingItem.price * 3
        };
        var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);
        //When
        var result = await controller.UpdateItemAsync(itemId, itemToUpdate);
        //Then
        result.Should().BeOfType<NoContentResult>();
        }

        [Fact]
        public async Task DeleteItemAsync_WithExistingItem_ReturnsNoContent()
        {
        //Given
        var existingItem = CreateRandomItem();
        repositoryStub.Setup(repo => repo.GetItemAsync(It.IsAny<Guid>()))
                .ReturnsAsync(expectedItem);

        var controller = new ItemsController(repositoryStub.Object, loggerStub.Object);
        //When
        var result = await controller.DeleteItemAsync(existingItem.Id);
        //Then
        result.Should().BeOfType<NoContentResult>();
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