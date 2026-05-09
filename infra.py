import boto3

def desplegar_infra():
    # Boto3 buscará automáticamente el token en tu archivo de credenciales
    s3 = boto3.client('s3')
    
    # Pon un nombre único (solo minúsculas y números)
    nombre_bucket = "inmobiliaria-aws-proyecto-final" 

    print("🛠 Creando infraestructura de almacenamiento...")
    try:
        # En la región de AWS Academy (us-east-1), no se necesita LocationConstraint
        s3.create_bucket(Bucket=nombre_bucket)
        print(f"✅ Bucket '{nombre_bucket}' creado en la nube.")
        
        # Activamos versionado para cumplir con Resiliencia
        s3.put_bucket_versioning(
            Bucket=nombre_bucket,
            VersioningConfiguration={'Status': 'Enabled'}
        )
        print("🛡 Resiliencia activada (Versionado de archivos).")
        
    except Exception as e:
        print(f"❌ Error: {e}")

if __name__ == "__main__":
    desplegar_infra()