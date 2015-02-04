using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConfigurationManager
{
    public class HipChat
    {
        public HipChat(List<String> pUser)
        {
            IDs = pUser;
        }

        public List<String> IDs { get; set; }
        private static String TOKEN = "1daa6e0d904eeedd68ec9a30135b23";
        private static String ROOM_ID = "944289";


        public String GetConnection()
        {
            return "http://api.hipchat.com/v1/rooms/history?room_id=944289&date=recent&timezone=UTC&format=json&auth_token=1daa6e0d904eeedd68ec9a30135b23";// "http://api.hipchat.com/v1/rooms/history?room_id=" + ROOM_ID + "&date=recent&timezone=UTC&format=json&auth_token=" + TOKEN;
        
        }
    }
}
