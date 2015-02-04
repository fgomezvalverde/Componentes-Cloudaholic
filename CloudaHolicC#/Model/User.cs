using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    public class User
    {
        public String NAME { get; set; }
        public int FILES_UPLOADED { get; set; }    // bit bucket

        public int HOME_WORKS { get; set; }       //base camp

        public int REGISTRED_HOURS { get; set; }  //togl

        public int CHAT_HOURS { get; set; }     //hipchat

        public String DATE { get; set; }

        public String MONTH { get; set; }
        public int TYPE { get; set; }
    }
}
