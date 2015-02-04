using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConfigurationManager
{
    public class Toggle

    {
        public Toggle(List<String> pTokens)
        {
            TOKENS = pTokens;
        }
        public List<String> TOKENS { get; set; }

        public int GetHourWorker(int cont)
        {
            
            var timeSrv = new Toggl.Services.TimeEntryService(TOKENS[cont]);
            var prams = new Toggl.QueryObjects.TimeEntryParams();

            prams.StartDate = Convert.ToDateTime("11/1/2012");
            prams.EndDate = DateTime.Now;

            var hours = timeSrv.List(prams)
                                    .Where(w => !string.IsNullOrEmpty(w.Description));

            var hours_list = hours.Select(s => s);
            int result = 0;
            foreach (var actual in hours_list)
            {
                result += Convert.ToInt32(actual.Duration + "");
            }
            return result / 60;
        }
    }
}
