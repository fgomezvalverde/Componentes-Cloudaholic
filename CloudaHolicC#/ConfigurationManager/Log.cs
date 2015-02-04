using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConfigurationManager
{
    public class Log
    {
        #region Constructors
        private Log() { }
        #endregion

        #region Properties
        public static string ActualLogKey
        {
            set
            {
                _actualLogKey = value;
            }
        }
        public static Log getInstance
        {
            get
            {
                return _logger;
            }
        }

        #endregion

        #region Methods
        public String sendLog(string pData)
        {
            lock (lockThis)
            {
                return _webClient.UploadString(_bodyLogKey + _actualLogKey, pData);
            }
        }
        #endregion

        #region Atributes
        private static readonly Log _logger = new Log();
        private static string _actualLogKey;
        private string _bodyLogKey = "https://logs.loggly.com/inputs/";
        private System.Net.WebClient _webClient = new System.Net.WebClient();
        private System.Object lockThis = new System.Object();
        #endregion

    }
}
