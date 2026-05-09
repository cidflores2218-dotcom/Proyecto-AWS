import boto3

def subir_a_nube():
    s3 = boto3.client('s3')
    nombre_bucket = "inmobiliaria-aws-proyecto-final" # PON EL MISMO DE ANTES
    archivo_local = "inmuebles.dat"
    
    print(f"☁️ Subiendo {archivo_local} a AWS S3...")
    try:
        s3.upload_file(archivo_local, nombre_bucket, archivo_local)
        print("✅ Sincronización exitosa. Los datos están seguros en la nube.")
    except Exception as e:
        print(f"❌ Error al subir: {e}")

if __name__ == "__main__":
    subir_a_nube()