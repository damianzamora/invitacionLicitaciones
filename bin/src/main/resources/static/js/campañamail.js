const app = Vue.createApp({
    
    data() {
        return {            
            campañaDelProveedor:[],
            myParamVar:"",
        }
        },

    created() {
        
        const urlParams = new URLSearchParams(window.location.search)
        const myParam = urlParams.get('id')
        this.myParamVar=myParam
        axios.get('/api/verCampaña/'+myParam)            //http://localhost:8080/campa%C3%B1aMail.html?id=1
        .then(res=> {
         this.campañaDelProveedor=res.data;
        })


    },

    methods: {                      
        generarPDF(){
            axios.post("/api/campañaProveedor/export/pdf", "tituloCampaña=" + this.campañaDelProveedor.titulo+"&"+"id="+this.myParamVar,
             { responseType: 'blob' })
                .then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'Detalles de'+" "+ this.campañaDelProveedor.titulo + '.pdf');
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

