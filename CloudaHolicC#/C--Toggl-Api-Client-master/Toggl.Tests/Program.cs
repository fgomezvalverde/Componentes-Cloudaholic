using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using Toggl.QueryObjects;

namespace Toggl.Tests
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {

            /*string[] my_args = { Assembly.GetExecutingAssembly().Location };

            int returnCode = NUnit.ConsoleRunner.Runner.Main(my_args);

            if (returnCode != 0)
                Console.Beep();*/
            var apiKey = "2d1d95cef10e17831ec505e9e6f9f7ea";
            var timeSrv = new Services.TimeEntryService(apiKey);
            var prams = new TimeEntryParams();

            // there is an issue with the date ranges have
            // to step out of the range on the end.
            // To capture the end of the billing range day + 1
            prams.StartDate = Convert.ToDateTime("11/1/2012");
            prams.EndDate = DateTime.Now;

            var hours = timeSrv.List(prams)
                                    .Where(w => !string.IsNullOrEmpty(w.Description));

            var hours_list = hours.Select(s => s);
            foreach (var actual in hours_list)
            {
                Console.WriteLine("Horas->" + actual.Duration);
            }
            Console.ReadLine();
        }
    }
}
