'use client'

import { createClient } from '@supabase/supabase-js'

const SUPABASE_URL = 'https://useffgexblwyqusvuvwr.supabase.co'
const SUPABASE_ANON_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVzZWZmZ2V4Ymx3eXF1c3Z1dndyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODQ5MTEyNzcsImV4cCI6MjEwMDQ4NzI3N30.IAJG6F5AQJMxcY03bv6ZUh_ISDkQfrMqCI5wk9mUfXQ'

export const supabase = createClient(SUPABASE_URL, SUPABASE_ANON_KEY)
