'use client'

import { createClient } from '@supabase/supabase-js'

const SUPABASE_URL = 'https://useffgexblwyqusvuvwr.supabase.co'
const SUPABASE_PUBLISHABLE_KEY = 'sb_publishable_CFdko6aMvmGQUM303Oxakg_b6698ao5'

export const supabase = createClient(SUPABASE_URL, SUPABASE_PUBLISHABLE_KEY)
