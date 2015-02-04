using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConfigurationManager
{
    public class Basecamp
    {
        public Basecamp(List<String> pTeam, String pOwner)
        {
            TEAM = pTeam;
            OWNER_ID = pOwner;
        }
        public List<String> TEAM { get; set; }
        public String OWNER_ID { get; set; }

        public String GetConnectionUser(int position)
        {
            return "https://basecamp.com/"+OWNER_ID+"/api/v1/people/" + TEAM[position] + ".json";
        }

        
    }
}
