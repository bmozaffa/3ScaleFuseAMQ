var config = {
development: {


    database: {
        host:   'localhost',
        user:   'root',
        password: 'ncPIGN8cKa5Aki4c',
        database: 'sampledb'
    },
    //3scale details
    threescale: {
         url: 'https://cicd-apicast-staging.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com:443/cicd/maingateway/',
         token: 'c07794ea12a274c855a302b1e7c17bb9'
    }
},
production: {
  

    //mongodb connection settings
    database: {
        host:   'mysql',
        user:   'root',
        password: 'ncPIGN8cKa5Aki4c',
        database: 'sampledb'
    },

    //3scale details
    threescale: {
        url: 'https://cicd-apicast-staging.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com:443/cicd/maingateway/',
        token: 'c07794ea12a274c855a302b1e7c17bb9'
    }

}
};
module.exports = config;
