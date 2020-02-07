Ext.define('AP.store.right.TestCheckStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.testCheckStore',
    fields: ['name', 'email', 'phone', 'active'],
    data: {
        items: [{
            name: 'Lisa',
            email: 'lisa@simpsons.com',
            phone: '555-111-1224',
            active: true
      }, {
            name: 'Bart',
            email: 'bart@simpsons.com',
            phone: '555-222-1234',
            active: true
      }, {
            name: 'Homer',
            email: 'home@simpsons.com',
            phone: '555-222-1244',
            active: false
      }, {
            name: 'Marge',
            email: 'marge@simpsons.com',
            phone: '555-222-1254',
            active: true
      }]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items',
            keepRawData: true
        }
    }

});