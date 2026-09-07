variable "neon_api_key" {
  description = "API key do Neon. Gere em: Neon Console > Account Settings > API Keys."
  type        = string
  sensitive   = true
}

variable "neon_org_id" {
  description = "ID da organização no Neon. Encontre em: Account Settings > Organization settings. Evite omitir: sem isso o projeto pode ser criado na organização errada."
  type        = string
}

variable "project_name" {
  description = "Nome do projeto no Neon."
  type        = string
  default     = "nubank-challenge"
}

variable "region_id" {
  description = "Região de deploy do projeto Neon (não pode ser alterada após a criação)."
  type        = string
  default     = "aws-sa-east-1" # São Paulo
}

variable "database_name" {
  description = "Nome do banco de dados padrão."
  type        = string
  default     = "nubank_db"
}

variable "role_name" {
  description = "Nome do role/usuário padrão do banco."
  type        = string
  default     = "nubank_app"
}
