terraform {
  required_version = ">= 1.14"

  required_providers {
    neon = {
      source  = "kislerdm/neon"
      version = "~> 0.6"
    }
  }
}

provider "neon" {
  api_key = var.neon_api_key
}

resource "neon_project" "nubank_challenge" {
  name       = var.project_name
  org_id     = var.neon_org_id
  pg_version = 16
  region_id  = var.region_id

  store_password = "yes"

  history_retention_seconds = 21600

  branch {
    name          = "main"
    database_name = var.database_name
    role_name     = var.role_name
  }

  default_endpoint_settings {
    autoscaling_limit_min_cu = 0.25
    autoscaling_limit_max_cu = 1
  }
}
