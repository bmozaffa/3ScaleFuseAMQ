var config = {
development: {


    database: {
        host:   'localhost',
        user:   'root',
        password: 'Hameed@123',
        database: 'sampledb'
    },
    //3scale details
    threescale: {
         url: 'https://cicd-apicast-staging.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com:443/cicd/maingateway/',
         token: '65f3eb43862f83427aba4f0811ae14ac'
    }
},
production: {
  

    //mongodb connection settings
    database: {
        host:   'mysql',
        user:   'dbuser',
        password: 'password',
        database: 'sampledb'
    },

    //3scale details
    threescale: {
        url: 'https://cicd-apicast-staging.app.rhdp.ocp.cloud.lab.eng.bos.redhat.com:443/cicd/maingateway/',
        token: '65f3eb43862f83427aba4f0811ae14ac'
    }

}
};
module.exports = config;
