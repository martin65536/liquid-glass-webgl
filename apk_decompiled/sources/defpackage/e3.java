package defpackage;

import android.graphics.Typeface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e3 implements q8, ak0, s41 {
    public Object a;
    public Object b;
    public Object c;
    public Object d;

    public e3(Typeface typeface, uc0 uc0Var) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        this.d = typeface;
        this.a = uc0Var;
        this.c = new vc0(1024);
        int a = uc0Var.a(6);
        if (a != 0) {
            int i6 = a + uc0Var.e;
            i = ((ByteBuffer) uc0Var.h).getInt(((ByteBuffer) uc0Var.h).getInt(i6) + i6);
        } else {
            i = 0;
        }
        this.b = new char[i * 2];
        int a2 = uc0Var.a(6);
        if (a2 != 0) {
            int i7 = a2 + uc0Var.e;
            i2 = ((ByteBuffer) uc0Var.h).getInt(((ByteBuffer) uc0Var.h).getInt(i7) + i7);
        } else {
            i2 = 0;
        }
        for (int i8 = 0; i8 < i2; i8++) {
            n31 n31Var = new n31(this, i8);
            tc0 b = n31Var.b();
            int a3 = b.a(4);
            if (a3 != 0) {
                i3 = ((ByteBuffer) b.h).getInt(a3 + b.e);
            } else {
                i3 = 0;
            }
            Character.toChars(i3, (char[]) this.b, i8 * 2);
            tc0 b2 = n31Var.b();
            int a4 = b2.a(16);
            if (a4 != 0) {
                int i9 = a4 + b2.e;
                i4 = ((ByteBuffer) b2.h).getInt(((ByteBuffer) b2.h).getInt(i9) + i9);
            } else {
                i4 = 0;
            }
            if (i4 > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                vc0 vc0Var = (vc0) this.c;
                tc0 b3 = n31Var.b();
                int a5 = b3.a(16);
                if (a5 != 0) {
                    int i10 = a5 + b3.e;
                    i5 = ((ByteBuffer) b3.h).getInt(((ByteBuffer) b3.h).getInt(i10) + i10);
                } else {
                    i5 = 0;
                }
                vc0Var.a(n31Var, 0, i5 - 1);
            } else {
                v7.m("invalid metadata codepoint length");
                throw null;
            }
        }
    }

    public static void h(e3 e3Var, tf0 tf0Var) {
        e3Var.getClass();
        tf0Var.getClass();
        if (((LinkedHashSet) e3Var.c).add(tf0Var)) {
            wf0 wf0Var = (wf0) e3Var.b;
            wf0Var.getClass();
            if (tf0Var.c == null) {
                wf0Var.e.addFirst(tf0Var);
                tf0Var.c = e3Var;
                wf0Var.b();
                return;
            }
            v7.i("Handler '", tf0Var, "' is already registered with a dispatcher");
        }
    }

    @Override // defpackage.ak0
    public boolean b() {
        ArrayList arrayList = (ArrayList) this.d;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((zj0) arrayList.get(i)).a.b()) {
                return true;
            }
        }
        return false;
    }

    @Override // defpackage.s41
    public i7 c(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        if (((i7) this.c) == null) {
            this.c = i7Var3.c();
        }
        i7 i7Var4 = (i7) this.c;
        if (i7Var4 != null) {
            int b = i7Var4.b();
            int i = 0;
            while (true) {
                i7 i7Var5 = (i7) this.c;
                if (i < b) {
                    if (i7Var5 != null) {
                        i7Var5.e(((j2) this.a).h(i).b(j, i7Var.a(i), i7Var2.a(i), i7Var3.a(i)), i);
                        i++;
                    } else {
                        o20.G("velocityVector");
                        throw null;
                    }
                } else {
                    if (i7Var5 != null) {
                        return i7Var5;
                    }
                    o20.G("velocityVector");
                    throw null;
                }
            }
        } else {
            o20.G("velocityVector");
            throw null;
        }
    }

    @Override // defpackage.s41
    public i7 d(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        if (((i7) this.b) == null) {
            this.b = i7Var.c();
        }
        i7 i7Var4 = (i7) this.b;
        if (i7Var4 != null) {
            int b = i7Var4.b();
            int i = 0;
            while (true) {
                i7 i7Var5 = (i7) this.b;
                if (i < b) {
                    if (i7Var5 != null) {
                        i7Var5.e(((j2) this.a).h(i).a(j, i7Var.a(i), i7Var2.a(i), i7Var3.a(i)), i);
                        i++;
                    } else {
                        o20.G("valueVector");
                        throw null;
                    }
                } else {
                    if (i7Var5 != null) {
                        return i7Var5;
                    }
                    o20.G("valueVector");
                    throw null;
                }
            }
        } else {
            o20.G("valueVector");
            throw null;
        }
    }

    @Override // defpackage.s41
    public i7 e(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        if (((i7) this.d) == null) {
            this.d = i7Var3.c();
        }
        i7 i7Var4 = (i7) this.d;
        if (i7Var4 != null) {
            int b = i7Var4.b();
            int i = 0;
            while (true) {
                i7 i7Var5 = (i7) this.d;
                if (i < b) {
                    if (i7Var5 != null) {
                        i7Var5.e(((j2) this.a).h(i).e(i7Var.a(i), i7Var2.a(i), i7Var3.a(i)), i);
                        i++;
                    } else {
                        o20.G("endVelocityVector");
                        throw null;
                    }
                } else {
                    if (i7Var5 != null) {
                        return i7Var5;
                    }
                    o20.G("endVelocityVector");
                    throw null;
                }
            }
        } else {
            o20.G("endVelocityVector");
            throw null;
        }
    }

    @Override // defpackage.s41
    public long f(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        int b = i7Var.b();
        long j = 0;
        for (int i = 0; i < b; i++) {
            j = Math.max(j, ((j2) this.a).h(i).d(i7Var.a(i), i7Var2.a(i), i7Var3.a(i)));
        }
        return j;
    }

    @Override // defpackage.ak0
    public float g() {
        return ((Number) ((q50) this.c).getValue()).floatValue();
    }

    public void i(gh0 gh0Var, int i) {
        if (i != 1 && i != 0) {
            throw new IllegalArgumentException(("Unsupported priority value: " + i).toString());
        }
        if (((LinkedHashSet) this.d).add(gh0Var)) {
            ((wf0) this.b).a(this, gh0Var, i);
        }
    }

    public void j(vf0 vf0Var, rf0 rf0Var) {
        wf0 wf0Var = (wf0) this.b;
        wf0Var.getClass();
        if (wf0Var.g == 0) {
            tf0 c = wf0Var.c(-1);
            wf0Var.f = c;
            wf0Var.g = -1;
            wf0Var.h = vf0Var;
            if (rf0Var != null) {
                if (c != null) {
                    c.d(rf0Var);
                }
                ky0 ky0Var = wf0Var.a;
                yf0 yf0Var = new yf0(rf0Var);
                ky0Var.getClass();
                ky0Var.j(null, yf0Var);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0056, code lost:
    
        if (r0 == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0071, code lost:
    
        if (r0 == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object k(long r8, long r10, defpackage.jj r12) {
        /*
            r7 = this;
            boolean r0 = r12 instanceof defpackage.cg0
            if (r0 == 0) goto L14
            r0 = r12
            cg0 r0 = (defpackage.cg0) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.j = r1
        L12:
            r12 = r0
            goto L1a
        L14:
            cg0 r0 = new cg0
            r0.<init>(r7, r12)
            goto L12
        L1a:
            java.lang.Object r0 = r12.h
            int r1 = r12.j
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L35
            if (r1 == r4) goto L31
            if (r1 != r3) goto L2b
            defpackage.o30.x(r0)
            goto L74
        L2b:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            return r2
        L31:
            defpackage.o30.x(r0)
            goto L59
        L35:
            defpackage.o30.x(r0)
            java.lang.Object r0 = r7.a
            gg0 r0 = (defpackage.gg0) r0
            if (r0 == 0) goto L43
            gg0 r0 = r0.E0()
            goto L44
        L43:
            r0 = r2
        L44:
            r5 = 0
            ik r1 = defpackage.ik.e
            if (r0 != 0) goto L5e
            java.lang.Object r7 = r7.b
            gg0 r7 = (defpackage.gg0) r7
            if (r7 == 0) goto L78
            r12.j = r4
            java.lang.Object r0 = r7.F0(r8, r10, r12)
            if (r0 != r1) goto L59
            goto L73
        L59:
            v41 r0 = (defpackage.v41) r0
            long r5 = r0.a
            goto L78
        L5e:
            java.lang.Object r7 = r7.a
            gg0 r7 = (defpackage.gg0) r7
            if (r7 == 0) goto L68
            gg0 r2 = r7.E0()
        L68:
            r7 = r2
            if (r7 == 0) goto L78
            r12.j = r3
            java.lang.Object r0 = r7.F0(r8, r10, r12)
            if (r0 != r1) goto L74
        L73:
            return r1
        L74:
            v41 r0 = (defpackage.v41) r0
            long r5 = r0.a
        L78:
            v41 r7 = new v41
            r7.<init>(r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.e3.k(long, long, jj):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object l(long r5, defpackage.jj r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof defpackage.dg0
            if (r0 == 0) goto L13
            r0 = r7
            dg0 r0 = (defpackage.dg0) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            dg0 r0 = new dg0
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.h
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2c
            if (r1 != r3) goto L26
            defpackage.o30.x(r7)
            goto L46
        L26:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r4)
            return r2
        L2c:
            defpackage.o30.x(r7)
            java.lang.Object r4 = r4.a
            gg0 r4 = (defpackage.gg0) r4
            if (r4 == 0) goto L39
            gg0 r2 = r4.E0()
        L39:
            if (r2 == 0) goto L4b
            r0.j = r3
            java.lang.Object r7 = r2.H0(r5, r0)
            ik r4 = defpackage.ik.e
            if (r7 != r4) goto L46
            return r4
        L46:
            v41 r7 = (defpackage.v41) r7
            long r4 = r7.a
            goto L4d
        L4b:
            r4 = 0
        L4d:
            v41 r6 = new v41
            r6.<init>(r4)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.e3.l(long, jj):java.lang.Object");
    }

    public hk m() {
        hk hkVar = (hk) ((vu) this.c).a();
        if (hkVar != null) {
            return hkVar;
        }
        v7.o("in order to access nested coroutine scope you need to attach dispatcher to the `Modifier.nestedScroll` first.");
        return null;
    }

    public i7 n(long j, i7 i7Var, i7 i7Var2) {
        float f;
        if (((i7) this.c) == null) {
            this.c = i7Var.c();
        }
        i7 i7Var3 = (i7) this.c;
        if (i7Var3 != null) {
            int b = i7Var3.b();
            int i = 0;
            while (true) {
                i7 i7Var4 = (i7) this.c;
                if (i < b) {
                    if (i7Var4 != null) {
                        j2 j2Var = (j2) this.a;
                        i7Var.getClass();
                        long j2 = j / 1000000;
                        gs a = ((hs) j2Var.f).a(i7Var2.a(i));
                        long j3 = a.c;
                        if (j3 > 0) {
                            f = ((float) j2) / ((float) j3);
                        } else {
                            f = 1.0f;
                        }
                        i7Var4.e((((Math.signum(a.a) * j5.a(f).b) * a.b) / ((float) j3)) * 1000.0f, i);
                        i++;
                    } else {
                        o20.G("velocityVector");
                        throw null;
                    }
                } else {
                    if (i7Var4 != null) {
                        return i7Var4;
                    }
                    o20.G("velocityVector");
                    throw null;
                }
            }
        } else {
            o20.G("velocityVector");
            throw null;
        }
    }

    public s51 o(wd wdVar, String str) {
        s51 s51Var;
        boolean isInstance;
        s51 a;
        synchronized (((ey0) this.d)) {
            try {
                s51Var = (s51) ((wb0) this.a).e.get(str);
                Class cls = wdVar.a;
                cls.getClass();
                Map map = wd.b;
                map.getClass();
                Integer num = (Integer) map.get(cls);
                if (num != null) {
                    isInstance = f31.D(num.intValue(), s51Var);
                } else {
                    if (cls.isPrimitive()) {
                        cls = o30.q(fp0.a(cls));
                    }
                    isInstance = cls.isInstance(s51Var);
                }
                if (isInstance) {
                    v51 v51Var = (v51) this.b;
                    if (v51Var instanceof qs0) {
                        qs0 qs0Var = (qs0) v51Var;
                        s51Var.getClass();
                        l80 l80Var = qs0Var.h;
                        if (l80Var != null) {
                            c4 c4Var = qs0Var.i;
                            c4Var.getClass();
                            o30.g(s51Var, c4Var, l80Var);
                        }
                    }
                    s51Var.getClass();
                } else {
                    ee0 ee0Var = new ee0((nk) this.c);
                    ee0Var.a.put(n20.s, str);
                    v51 v51Var2 = (v51) this.b;
                    try {
                        try {
                            a = v51Var2.c(wdVar, ee0Var);
                        } catch (AbstractMethodError unused) {
                            Class cls2 = wdVar.a;
                            cls2.getClass();
                            a = v51Var2.b(cls2, ee0Var);
                        }
                    } catch (AbstractMethodError unused2) {
                        Class cls3 = wdVar.a;
                        cls3.getClass();
                        a = v51Var2.a(cls3);
                    }
                    s51Var = a;
                    wb0 wb0Var = (wb0) this.a;
                    s51Var.getClass();
                    s51 s51Var2 = (s51) wb0Var.e.put(str, s51Var);
                    if (s51Var2 != null) {
                        s51Var2.a();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return s51Var;
    }

    @Override // defpackage.s41
    public /* synthetic */ void a() {
    }

    public e3(wb0 wb0Var, v51 v51Var, nk nkVar) {
        nkVar.getClass();
        this.a = wb0Var;
        this.b = v51Var;
        this.c = nkVar;
        this.d = new ey0(4);
    }

    public /* synthetic */ e3(Object obj) {
        this.a = obj;
    }

    public e3(ls lsVar) {
        this(new j2(29, lsVar));
    }
}
