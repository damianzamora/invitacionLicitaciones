const app = Vue.createApp({
    
    data() {
        return {            
            campaniaDelProveedor:[],
            myParamVar:"",
        }
        },
    created() {
        const urlParams = new URLSearchParams(window.location.search)
        const myParam = urlParams.get('id')
        this.myParamVar=myParam
        axios.get('/api/verCampaniaReferente/'+myParam)            //http://localhost:8080/linkcampania.html?id=1
        .then(res=> {
         this.campaniaDelProveedor=res.data;         
        })
    },

    methods: {                      
        generarPDF(){
            axios.post("/api/campaniaReferenteExportPdf", "tituloCampania=" + this.campaniaDelProveedor.campania.titulo+"&"+"id="+this.myParamVar,
             { responseType: 'blob' })
                .then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'Detalles de'+" "+ this.campaniaDelProveedor.titulo + '.pdf');
                    document.body.appendChild(link);
                    link.click(); 
                    Swal.fire('Se inicio descarga de PDF','','success',)                   
                })
                .catch(err => {
                    Swal.fire("Error","No se pudo localizar archivo de campaña","error")
                })    
        }
    },   
    computed: {
    }, 
})
app.mount("#app")

