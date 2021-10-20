using System;
using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace HighscoreApi.Controllers
{
    public class MQController : IMQController
    {
        ConnectionFactory factory;
        IConnection connection;
        IModel channel;
        const string queueName = "highscore";

        public MQController()
        {
            setUpConnection();
            listenForMessages();
        }

        private void setUpConnection(){
            Uri uri = new Uri("amqps://iaqmcoek:EuPEe_Au1nAuEa8DD8tWstWP3VwFwV1m@rattlesnake.rmq.cloudamqp.com/iaqmcoek");
            factory = new ConnectionFactory() { Uri = uri };
            connection = factory.CreateConnection();
            channel = connection.CreateModel();
        }

        private void listenForMessages(){
            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, ea) =>
            {
                var body = ea.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                Console.WriteLine(" [x] Received {0}", message);
            };
            channel.BasicConsume(queue: queueName,
                                 autoAck: true,
                                 consumer: consumer);
        }
    }
}