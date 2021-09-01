FROM mcr.microsoft.com/dotnet/aspnet:5.0-focal AS base
WORKDIR /app
EXPOSE 5010

ENV ASPNETCORE_URLS=http://*:$PORT

# Creates a non-root user with an explicit UID and adds permission to access the /app folder
# For more info, please refer to https://aka.ms/vscode-docker-dotnet-configure-containers

FROM mcr.microsoft.com/dotnet/sdk:5.0-focal AS build
WORKDIR /src
COPY ["./LoginApi.csproj", "./"]
RUN dotnet restore "LoginApi.csproj"
COPY . .
WORKDIR "/src/."
RUN dotnet build "LoginApi.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "LoginApi.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
CMD ASPNETCORE_URLS=http://*:$PORT dotnet LoginApi.dll
