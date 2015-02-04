using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;
namespace ConfigurationManager
{
    public class BitBucket
    {
        public BitBucket(List<String> pId)
        {
            IDs = pId;
        }
        public List<String> IDs { get; set; }
        

       private static string OWNER_NAME = "arquitecto";
       private static string PROJECT_NAME = "loopy";
       public string CONNECTION = "https://bitbucket.org/api/2.0/repositories/" + OWNER_NAME + "/" + PROJECT_NAME + "/" + "commits/";
       
        

    }

        

    }

