output "project_id" {
  description = "Identificador do projeto no Neon."
  value       = neon_project.nubank_challenge.id
}

output "database_host" {
  description = "Host do banco (mapeia para POSTGRES_HOST)."
  value       = neon_project.nubank_challenge.database_host
}

output "database_name" {
  description = "Nome do banco (mapeia para POSTGRES_DB)."
  value       = neon_project.nubank_challenge.database_name
}

output "database_user" {
  description = "Usuário do banco (mapeia para POSTGRES_USER)."
  value       = neon_project.nubank_challenge.database_user
}

output "database_password" {
  description = "Senha do banco (mapeia para POSTGRES_PASSWORD)."
  value       = neon_project.nubank_challenge.database_password
  sensitive   = true
}

output "connection_uri" {
  description = "Connection string completa (conexão direta, sem pooler). Use para clientes de curta duração ou ferramentas administrativas."
  value       = neon_project.nubank_challenge.connection_uri
  sensitive   = true
}

output "connection_uri_pooler" {
  description = "Connection string via pooler (PgBouncer). Recomendada para a aplicação, especialmente em ambientes serverless/com muitas conexões curtas."
  value       = neon_project.nubank_challenge.connection_uri_pooler
  sensitive   = true
}
