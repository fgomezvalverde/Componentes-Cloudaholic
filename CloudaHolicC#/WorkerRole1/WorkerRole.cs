using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.WindowsAzure;
using Microsoft.WindowsAzure.Diagnostics;
using Microsoft.WindowsAzure.ServiceRuntime;
using Microsoft.WindowsAzure.Storage;
using Brisebois.WindowsAzure.Rest;
using ConfigurationManager;
using Model;
using log4net;
using System.Text;
using Newtonsoft.Json;
using System.Globalization;
using Toggl.QueryObjects;
namespace WorkerRole1
{
    public class WorkerRole : RoleEntryPoint
    {
        private readonly CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        private readonly ManualResetEvent runCompleteEvent = new ManualResetEvent(false);
        private ILog logger ;
        private List<User> team;
        private Basecamp basecamp;
        private Toggle toggl;
        private HipChat hipchat;
        private BitBucket bitbucket;
        public override void Run()
        {
            for (;;)
            {
                CleanTeam();  // SE LIMPIA EL TEAM CON VALORES DE 0 y nueva fecha


                BitBucket();
                BaseCamp();
                Toggl();
                HipChat();


                // SE ENVIA EL TEAM A GOOGLE

                SendData();
                Create();

                Trace.WriteLine("Empieza role work a dormir","Work Role");
                logger.Info("Se termino procesos WorkRole");

                Thread.Sleep(TImesConfiguration.TIME_WAIT);
            }
           
        }



        public void Toggl()
        {
            try{
                for(int cont=0; cont < team.Count;cont++)
                {
                    int minutes = toggl.GetHourWorker(cont);
                    team.Where(w => w.NAME == team[cont].NAME).ToList().ForEach(s => s.REGISTRED_HOURS= minutes);
                }
                Trace.WriteLine("Se termino proceso", "Toggl");
                logger.Info("Se termino procesos Toggl");
            }
            catch(Exception ex)
            {
                Trace.WriteLine(ex.Message, "Error,Toggl");
                logger.Info("Error,Toggl:"+ex.Message);
            }

        }

        public void HipChat()
        {
            try
            {
                HttpWebRequest request = (HttpWebRequest)
                            WebRequest.Create(hipchat.GetConnection());
                request.ContentType = "application/json";
                HttpWebResponse response = (HttpWebResponse)
                    request.GetResponse();
                WebHeaderCollection header = response.Headers;

                var encoding = ASCIIEncoding.ASCII;
                using (var reader = new System.IO.StreamReader(response.GetResponseStream(), encoding))
                {

                    dynamic json = Newtonsoft.Json.JsonConvert.DeserializeObject(reader.ReadToEnd());
                    var mensajes = json.messages;
                    foreach (var msg in mensajes)
                    {
                        var autor = msg.from;
                        string nombre = autor.user_id;

                        for (int cont = 0; cont < team.Count; cont++)
                        {

                            if (hipchat.IDs[cont].Equals(nombre))
                            {
                                team.Where(w => w.NAME == team[cont].NAME).ToList().ForEach(s => s.CHAT_HOURS++);
                                break;
                            }
                        }
                    }


                }
                Trace.WriteLine("Se termino proceso", "HipChat");
                logger.Info("Se termino procesos HipChat");
            }
            catch (Exception ex)
            {
                Trace.WriteLine(ex.Message, "Error,HipChat");
                logger.Info("Error,HipChat:" + ex.Message);
            }
        }

        public void BitBucket()
        {
            
            Action<Uri, HttpStatusCode, string> onError = (uri, code, message) =>
            {
                Trace.WriteLine(message,"ERROR,BitBucket");
                logger.Error("Error,BitBucket"+message);
            };
            try
            {
                var result = RestClient.Uri(bitbucket.CONNECTION).GetAsync(onError);
            
                dynamic json = Newtonsoft.Json.JsonConvert.DeserializeObject(result.Result);
                var values = json.values;
                foreach (var commit in values)
                {
                    var autor = commit.author;
                    var usuario = autor.user;
                    string nombre = (string)usuario.username;
                    for (int cont = 0; cont < team.Count; cont++)
                    {
                        if (bitbucket.IDs[cont].Equals(nombre))
                        {
                            team.Where(w => w.NAME == team[cont].NAME).ToList().ForEach(s => s.FILES_UPLOADED++);
                            break;
                        }
                    }
                }
                Trace.WriteLine("Se termino proceso", "BitBucket");
                logger.Info("Se termino procesos BitBucket");
            }
            catch (Exception ex)
            {
                Trace.WriteLine(ex.Message, "Error,BitBucket");
                logger.Info("Error,BitBucket:" + ex.Message);
            }

            
            
        }

        public void BaseCamp()
        {
            try
            {
                string autorization = "fafa_gttm@hotmail.com" + ":" + "volumen25510440";
                byte[] binaryAuthorization = System.Text.Encoding.UTF8.GetBytes(autorization);
                autorization = Convert.ToBase64String(binaryAuthorization);
                autorization = "Basic " + autorization;

                for (int cont = 0; cont < team.Count; cont++)
                {

                    HttpWebRequest request = (HttpWebRequest)
                        WebRequest.Create(basecamp.GetConnectionUser(cont));
                    //request.ContentType = "application/json";
                    request.UserAgent = "loopy (fafa_gttm@hotmail.com)";
                    request.Headers.Add("AUTHORIZATION", autorization);
                    // execute the request
                    HttpWebResponse response = (HttpWebResponse)
                        request.GetResponse();
                    WebHeaderCollection header = response.Headers;

                    var encoding = ASCIIEncoding.ASCII;
                    using (var reader = new System.IO.StreamReader(response.GetResponseStream(), encoding))
                    {
                        string responseText = reader.ReadToEnd();
                        dynamic json = Newtonsoft.Json.JsonConvert.DeserializeObject(responseText);
                        var tareas = json.assigned_todos;
                        var count = tareas.count;
                        team.Where(w => w.NAME == team[cont].NAME).ToList().ForEach(s => s.HOME_WORKS = Convert.ToInt32(count + ""));
                        Trace.WriteLine("Se cambio a " + team[cont].NAME + " a -> " + team[cont].HOME_WORKS);
                    }


                }
                Trace.WriteLine("Se termino proceso", "BaseCamp");
                logger.Info("Se termino procesos BaseCamp");
            }
            catch (Exception ex)
            {
                Trace.WriteLine(ex.Message, "Error,BaseCamp");
                logger.Info("Error,BaseCamp:" + ex.Message);
            }
            
        }

        /// <summary>
        /// ENVIA EL TEAM A GOOGLE
        /// </summary>
        public void SendData()
        {
            try
            {
                Team all_team = new Team { NAME = "DOTEROS", USERS = team };
                string json = JsonConvert.SerializeObject(all_team);
                

                HttpWebRequest request = (HttpWebRequest)
                    WebRequest.Create("http://localhost:8888/cloudaholic?json=" + json);

                // execute the request
                HttpWebResponse response = (HttpWebResponse)
                    request.GetResponse();
                WebHeaderCollection header = response.Headers;

                var encoding = ASCIIEncoding.ASCII;
                using (var reader = new System.IO.StreamReader(response.GetResponseStream(), encoding))
                {
                    string responseText = reader.ReadToEnd();
                    Trace.WriteLine("Se recibio satifactoriamente en GAE", "SendTeam");
                    Trace.WriteLine("JSON DE TEAM\n\n" + json);
                    logger.Info("Se recibio satifactoriamente en GAE");
                }
            }
            catch (Exception ex)
            {
                Trace.WriteLine(ex.Message, "Error,SendTeam");
                logger.Info("Error,SendTeam:" + ex.Message);
            }
            
        }

        /// <summary>
        /// SETEA LOS VALORES INICIALES DE LAS TECNOLOGIAS INTEGRADAS
        /// </summary>
        /// <returns></returns>
        public override bool OnStart()
        {
            basecamp = new Basecamp(new List<String>() { "me","9758774"}, "2800253");
            toggl = new ConfigurationManager.Toggle ( new List<String>() { "8b762df09a9e83b5f6938eca520299ea", "10ebfe0097b8ade692d71428c24e8df5" });
            hipchat = new HipChat(new List<String>() {"1384708","1464129" });
            bitbucket = new BitBucket(new List<String>() { "arquitecto", "aexarahi" });
            logger =  LogManager.GetLogger(typeof(User));
            return base.OnStart();
        }

        public void CleanTeam()
        {
            team = new List<User>() { new User { NAME = "Fabian Gomez" ,CHAT_HOURS=0,DATE = DateTime.Now.ToString(),FILES_UPLOADED=0,HOME_WORKS=0,MONTH=DateTime.Today.ToString("MMM", CultureInfo.InvariantCulture),REGISTRED_HOURS=0,TYPE=0}
            , new User { NAME = "Andrea Exarahi" ,CHAT_HOURS=0,DATE = DateTime.Now.ToString(),FILES_UPLOADED=0,HOME_WORKS=0,MONTH=DateTime.Today.ToString("MMM", CultureInfo.InvariantCulture),REGISTRED_HOURS=0,TYPE=0} };
        }

        public override void OnStop()
        {
            Trace.TraceInformation("WorkerRole1 is stopping");

            this.cancellationTokenSource.Cancel();
            this.runCompleteEvent.WaitOne();

            base.OnStop();

            Trace.TraceInformation("WorkerRole1 has stopped");
        }

        private async Task RunAsync(CancellationToken cancellationToken)
        {
            // TODO: Reemplazar lo siguiente por su propia lógica.
            while (!cancellationToken.IsCancellationRequested)
            {
                Trace.TraceInformation("Working");
                await Task.Delay(1000);
            }
        }
        public void Create()
        {
            HttpWebRequest request = (HttpWebRequest)
                WebRequest.Create("http://localhost:8888/cloudaholic/ServletDatabase?name=" + "DOTEROS");

            // execute the request
            HttpWebResponse response = (HttpWebResponse)
                request.GetResponse();

        }
    }
}
