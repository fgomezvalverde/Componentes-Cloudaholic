using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
namespace ConfigurationManager
{
    
        public class LogglyManager
        {
            #region Constructors
            private LogglyManager()
            {

            }
            #endregion

            #region Properties
            public static LogglyManager getInstance
            {
                get
                {
                    return _loggly;
                }
            }
            #endregion

            #region Methods
            /// <summary>
            /// set the key for Json use 
            /// </summary>
            /// <param name="pDictionary"></param>
            public void sendLog(Object pData)
            {
                if (pData != null)
                {
                    var jsonLogEntry = JsonConvert.SerializeObject(pData);
                    setLogKey(1);
                    transactionLog.sendLog(jsonLogEntry);
                }

            }
            /// <summary>
            /// send strings , and change the key for string table
            /// </summary>
            /// <param name="pData"></param>
            internal void sendLog(String pData)
            {
                if (pData != null)
                {
                    setLogKey(0);
                    transactionLog.sendLog(pData);
                }

            }
            /// <summary>
            /// Change the current key
            /// </summary>
            /// <param name="pNewKey"></param>
            private void setLogKey(int pNewKey)
            {
                Log.ActualLogKey = keys[pNewKey];
            }
            #endregion

            #region Atributes
            private static readonly LogglyManager _loggly = new LogglyManager();
            private static Log transactionLog = Log.getInstance;

            private System.Net.WebClient _webClient = new System.Net.WebClient();

            /// <summary>
            /// keys for tables, we can create more on loggly or generate a new key
            /// </summary>
            private string[] keys = new String[] { "5ff55507-4ef6-4ddf-8c1d-cae80369fbbf", "9e9c6037-3548-4974-b89a-cc733c9e6acc" };


            //private IDictionary<string, int> _dictionary = new Dictionary<string, int>(); //
            // key[0] = key para jsons
            // key[1] = kry para stings

            #endregion

        }

    }

