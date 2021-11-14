const app = Vue.createApp({
    
    data() {
        return {            
            proveedores:[],
            campañas:[],        
            campañasProveedores:[],  
            nombreCampaña:"",
            descripcionCampaña:"", 
            optionTipo:"",
            campañaTipo:"",
            campañaSeleccionada:"",
            asuntoMail:"",
            cuerpoMail:"",
            proveedoresSeleccionados:"",
            proveedoresSeleccionadosArr:[],
        }
        },

    created() {
        axios.get("/api/campañas")
        .then(res => {
            this.campañas = res.data            
        }),
        axios.get("/api/proveedores")
        .then(res => {
            this.proveedores = res.data            
        }),
        axios.get("/api/campañaProveedor")
        .then(res => {
            this.campañasProveedores = res.data    
        })     
    },
    methods: {                      
        agregarCampaña(){          
            Swal.fire({
                title: '¿Desea crear la campaña',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/crearCampaña',
                    "nombreCampaña="+this.nombreCampaña+"&"+"descripcionCampaña="+this.descripcionCampaña,                                  
                    {headers:{'accept':'application/xml'}})
                    .then(res => Swal.fire(
                   'Camaña creada con éxito!',
                    '',
                    'success',))
                  .then(response => location.reload())                  
                  .catch(res=> Swal.fire(res.response.data,"Error en los datos","error"))              
                }
              })   
        },
        enviarCampañasPorMail(){
            Swal.fire({
                title: '¿Desea enviar solicitud de campaña?',
                text: "se enviará a los proveedores seleccionados.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/solicitudCampaña',
                    "campañaSeleccionada="+this.campañaSeleccionada+"&"+"asuntoMail="+this.asuntoMail+"&"+
                    "cuerpoMail="+this.cuerpoMail+"&"+"proveedoresSeleccionadosArr="+this.proveedoresSeleccionadosArr,                                
                    {headers:{'accept':'application/xml'}})
                    .then(res => Swal.fire(
                   'Solicitud enviada con éxito!',
                    '',
                    'success',))
                  .then(response => location.reload())                  
                  .catch(res=> Swal.fire(res.response.data,"Error en los datos","error"))              
                }
              })  
        },
        
        agregarProveedor(proveedor){                
                this.proveedoresSeleccionadosArr.push(proveedor.nombre)
                console.log(this.proveedoresSeleccionadosArr)
        },
        generarPDF(campaña){
            axios.post("/api/campaña/export/pdf", "tituloCampaña=" + campaña.titulo,
             { responseType: 'blob' })
                .then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'Detalles de'+" "+ campaña.titulo + '.pdf');
                    document.body.appendChild(link);
                    link.click();                   
                })
                .catch(err => {
                    Swal.fire(res.response.data,"Error al generar la factura","error")
                })    

        }
       
        
    },   

    computed: {
      
    }, 
    
})

app.mount("#app")

