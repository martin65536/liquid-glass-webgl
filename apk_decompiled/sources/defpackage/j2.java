package defpackage;

import android.content.Context;
import android.graphics.Region;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillManager;
import androidx.profileinstaller.ProfileInstallReceiver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class j2 implements nq, os, nq0, wn0 {
    public static final j2 g = new j2(0, new float[]{0.8951f, -0.7502f, 0.0389f, 0.2664f, 1.7135f, -0.0685f, -0.1614f, 0.0367f, 1.0296f});
    public final /* synthetic */ int e;
    public Object f;

    public j2(int i) {
        rt rtVar;
        this.e = i;
        switch (i) {
            case 9:
                this.f = new HashMap();
                return;
            case 12:
                ac0 ac0Var = new ac0();
                this.f = ac0Var;
                if (!ac0Var.f) {
                    if (ac0Var.g) {
                        dn0.a("ManagedValuesStore tried to enter composition twice. Did you attempt to install the same store multiple times or into two compositions?");
                    }
                    ac0Var.a();
                    ac0Var.g = true;
                    return;
                }
                return;
            case 13:
                this.f = new CopyOnWriteArrayList();
                new HashMap();
                return;
            case 16:
                if (Build.VERSION.SDK_INT >= 28) {
                    rtVar = new rt(18);
                } else {
                    rtVar = new rt(19);
                }
                this.f = rtVar;
                return;
            case 17:
                this.f = new kb0();
                return;
            case 20:
                this.f = new he0();
                return;
            case 22:
                this.f = new Region();
                return;
            default:
                this.f = new TreeSet(dl.j);
                return;
        }
    }

    @Override // defpackage.nq
    public void a(final o4 o4Var) {
        ii iiVar = new ii("EmojiCompatInitializer");
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), iiVar);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor.execute(new Runnable() { // from class: qq
            @Override // java.lang.Runnable
            public final void run() {
                j2 j2Var = j2.this;
                o4 o4Var2 = o4Var;
                ThreadPoolExecutor threadPoolExecutor2 = threadPoolExecutor;
                try {
                    hu l = jc0.l((Context) j2Var.f);
                    if (l != null) {
                        gu guVar = (gu) l.a;
                        synchronized (guVar.h) {
                            guVar.j = threadPoolExecutor2;
                        }
                        l.a.a(new rq(o4Var2, threadPoolExecutor2));
                        return;
                    }
                    throw new RuntimeException("EmojiCompat font provider not available on this device.");
                } catch (Throwable th) {
                    o4Var2.R(th);
                    threadPoolExecutor2.shutdown();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0039  */
    @Override // defpackage.os
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object b(defpackage.ps r8, defpackage.ij r9) {
        /*
            r7 = this;
            int r0 = r7.e
            ik r1 = defpackage.ik.e
            r2 = 1
            x31 r3 = defpackage.x31.a
            switch(r0) {
                case 8: goto L69;
                default: goto La;
            }
        La:
            boolean r0 = r9 instanceof defpackage.s
            if (r0 == 0) goto L1d
            r0 = r9
            s r0 = (defpackage.s) r0
            int r4 = r0.k
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L1d
            int r4 = r4 - r5
            r0.k = r4
            goto L22
        L1d:
            s r0 = new s
            r0.<init>(r7, r9)
        L22:
            java.lang.Object r9 = r0.i
            int r4 = r0.k
            if (r4 == 0) goto L39
            if (r4 != r2) goto L32
            tr0 r7 = r0.h
            defpackage.o30.x(r9)     // Catch: java.lang.Throwable -> L30
            goto L5a
        L30:
            r8 = move-exception
            goto L65
        L32:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            r1 = 0
            goto L5e
        L39:
            defpackage.o30.x(r9)
            tr0 r9 = new tr0
            yj r4 = r0.f
            r4.getClass()
            r9.<init>(r8, r4)
            r0.h = r9     // Catch: java.lang.Throwable -> L63
            r0.k = r2     // Catch: java.lang.Throwable -> L63
            java.lang.Object r7 = r7.f     // Catch: java.lang.Throwable -> L5f
            kv r7 = (defpackage.kv) r7     // Catch: java.lang.Throwable -> L5f
            java.lang.Object r7 = r7.d(r9, r0)     // Catch: java.lang.Throwable -> L5f
            if (r7 != r1) goto L55
            goto L56
        L55:
            r7 = r3
        L56:
            if (r7 != r1) goto L59
            goto L5e
        L59:
            r7 = r9
        L5a:
            r7.l()
            r1 = r3
        L5e:
            return r1
        L5f:
            r7 = move-exception
            r8 = r7
        L61:
            r7 = r9
            goto L65
        L63:
            r8 = move-exception
            goto L61
        L65:
            r7.l()
            throw r8
        L69:
            cp0 r0 = new cp0
            r0.<init>()
            java.lang.Object r7 = r7.f
            j2 r7 = (defpackage.j2) r7
            zn r4 = new zn
            r4.<init>(r2, r0, r8)
            java.lang.Object r7 = r7.b(r4, r9)
            if (r7 != r1) goto L7e
            r3 = r7
        L7e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.j2.b(ps, ij):java.lang.Object");
    }

    @Override // defpackage.wn0
    public void d() {
        Log.d("ProfileInstaller", "DIAGNOSTIC_PROFILE_IS_COMPRESSED");
    }

    @Override // defpackage.wn0
    public void e(int i, Object obj) {
        String str;
        switch (i) {
            case 1:
                str = "RESULT_INSTALL_SUCCESS";
                break;
            case 2:
                str = "RESULT_ALREADY_INSTALLED";
                break;
            case 3:
                str = "RESULT_UNSUPPORTED_ART_VERSION";
                break;
            case 4:
                str = "RESULT_NOT_WRITABLE";
                break;
            case 5:
                str = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                break;
            case 6:
                str = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                break;
            case 7:
                str = "RESULT_IO_EXCEPTION";
                break;
            case 8:
                str = "RESULT_PARSE_EXCEPTION";
                break;
            case 9:
            default:
                str = "";
                break;
            case 10:
                str = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                break;
            case 11:
                str = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                break;
        }
        if (i != 6 && i != 7 && i != 8) {
            Log.d("ProfileInstaller", str);
        } else {
            Log.e("ProfileInstaller", str, (Throwable) obj);
        }
        ((ProfileInstallReceiver) this.f).setResultCode(i);
    }

    public void f(z40 z40Var) {
        if (!z40Var.E()) {
            q00.b("DepthSortedSet.add called on an unattached node");
        }
        ((rx0) this.f).add(z40Var);
    }

    public void g() {
        ((th) this.f).getClass();
    }

    public ls h(int i) {
        switch (this.e) {
            case 27:
                return ((ms[]) this.f)[i];
            case 28:
                return (ms) this.f;
            default:
                return (ls) this.f;
        }
    }

    public hy0 i() {
        oq a = oq.a();
        if (a.b() == 1) {
            return new pz(true);
        }
        ik0 B = n30.B(Boolean.FALSE);
        ul ulVar = new ul(B, this);
        a.a.writeLock().lock();
        try {
            if (a.c != 1 && a.c != 2) {
                a.b.add(ulVar);
                a.a.writeLock().unlock();
                return B;
            }
            a.d.post(new mq(Arrays.asList(ulVar), a.c, null));
            a.a.writeLock().unlock();
            return B;
        } catch (Throwable th) {
            a.a.writeLock().unlock();
            throw th;
        }
    }

    public void j(float f, float f2, float f3, float f4) {
        r7 r7Var = (r7) this.f;
        uc q = r7Var.q();
        float intBitsToFloat = Float.intBitsToFloat((int) (r7Var.v() >> 32)) - (f3 + f);
        float intBitsToFloat2 = Float.intBitsToFloat((int) (r7Var.v() & 4294967295L)) - (f4 + f2);
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        if (Float.intBitsToFloat((int) (floatToRawIntBits >> 32)) < 0.0f || Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)) < 0.0f) {
            p00.a("Width and height must be greater than or equal to zero");
        }
        r7Var.G(floatToRawIntBits);
        q.d(f, f2);
    }

    public void k(View view, int i, boolean z) {
        if (Build.VERSION.SDK_INT >= 27) {
            ((AutofillManager) this.f).notifyViewVisibilityChanged(view, i, z);
        }
    }

    public c4 l(c4 c4Var, b4 b4Var) {
        Object obj;
        long j;
        boolean z;
        long I;
        kb0 kb0Var = (kb0) this.f;
        List list = (List) c4Var.f;
        kb0 kb0Var2 = new kb0(list.size());
        int size = list.size();
        int i = 0;
        while (i < size) {
            wm0 wm0Var = (wm0) list.get(i);
            long j2 = wm0Var.a;
            int n = o4.n(kb0Var.f, kb0Var.h, j2);
            if (n < 0 || (obj = kb0Var.g[n]) == jc0.g) {
                obj = null;
            }
            vm0 vm0Var = (vm0) obj;
            if (vm0Var == null) {
                j = wm0Var.b;
                I = wm0Var.d;
                z = false;
            } else {
                j = vm0Var.a;
                z = vm0Var.c;
                I = b4Var.I(vm0Var.b);
            }
            long j3 = wm0Var.a;
            int i2 = i;
            List list2 = list;
            int i3 = size;
            kb0Var2.b(j3, new um0(j3, wm0Var.b, wm0Var.d, wm0Var.e, wm0Var.f, j, I, z, wm0Var.g, wm0Var.i, wm0Var.j, wm0Var.k, wm0Var.l, wm0Var.m));
            boolean z2 = wm0Var.e;
            if (z2) {
                kb0Var.b(j2, new vm0(wm0Var.b, wm0Var.c, z2));
            } else {
                kb0Var.c(j2);
            }
            i = i2 + 1;
            list = list2;
            size = i3;
        }
        return new c4(8, kb0Var2, c4Var);
    }

    public boolean m(z40 z40Var) {
        if (!z40Var.E()) {
            q00.b("DepthSortedSet.remove called on an unattached node");
        }
        return ((rx0) this.f).remove(z40Var);
    }

    public Object n(ed edVar, vu vuVar) {
        lw0 lw0Var;
        jv0 jv0Var;
        int i;
        if (((gh) this.f) == null) {
            cn0.b("Called runAndWatch on a manager that has been disposed of");
        }
        gh ghVar = (gh) this.f;
        if ((ghVar instanceof lw0) && (jv0Var = (lw0Var = (lw0) ghVar).f) != null && !jv0Var.equals(edVar)) {
            ce0 ce0Var = new ce0();
            jv0 jv0Var2 = lw0Var.f;
            if (jv0Var2 == null) {
                cn0.b("promote must only be called when a manager is managing subscriptions for one channel and needs to start managing them for a second");
            }
            we0 we0Var = lw0Var.d;
            ArrayList arrayList = ce0Var.c;
            if (we0Var == null) {
                Object obj = lw0Var.b;
                obj.getClass();
                arrayList.add(new zd0(obj, jv0Var2));
            } else {
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j = jArr[i2];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8;
                            int i4 = 8 - ((~(i2 - length)) >>> 31);
                            int i5 = 0;
                            while (i5 < i4) {
                                if ((j & 255) < 128) {
                                    i = i3;
                                    arrayList.add(new zd0(objArr[(i2 << 3) + i5], jv0Var2));
                                } else {
                                    i = i3;
                                }
                                j >>= i;
                                i5++;
                                i3 = i;
                            }
                            if (i4 != i3) {
                                break;
                            }
                        }
                        if (i2 == length) {
                            break;
                        }
                        i2++;
                    }
                }
            }
            ce0Var.e();
            lw0Var.g();
            this.f = ce0Var;
        }
        gh ghVar2 = (gh) this.f;
        ghVar2.getClass();
        ww0 u = cx0.j().u(ghVar2.i(edVar));
        ghVar2.d(edVar);
        try {
            ww0 j2 = u.j();
            try {
                Object a = vuVar.a();
                u.c();
                ghVar2.e();
                return a;
            } finally {
                ww0.q(j2);
            }
        } catch (Throwable th) {
            u.c();
            throw th;
        }
    }

    public void o(float f, float f2, long j) {
        uc q = ((r7) this.f).q();
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        q.d(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
        q.a(f, f2);
        q.d(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
    }

    public void p(z10 z10Var) {
        ((Region) this.f).set(z10Var.a, z10Var.b, z10Var.c, z10Var.d);
    }

    public void q(float f, float f2) {
        ((r7) this.f).q().d(f, f2);
    }

    public String toString() {
        switch (this.e) {
            case 0:
                return "Bradford";
            case 5:
                return ((rx0) this.f).toString();
            default:
                return super.toString();
        }
    }

    public /* synthetic */ j2(int i, boolean z) {
        this.e = i;
    }

    public j2(mm mmVar) {
        this.e = 25;
        this.f = new hs(yx0.a, mmVar);
    }

    public /* synthetic */ j2(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    public j2(View view) {
        this.e = 10;
        this.f = view;
        n30.z(new n9(7, this));
    }

    public j2(long[] jArr) {
        ke0 ke0Var;
        this.e = 24;
        if (jArr != null) {
            long[] copyOf = Arrays.copyOf(jArr, jArr.length);
            ke0Var = new ke0(copyOf.length);
            int i = ke0Var.b;
            if (i >= 0) {
                if (copyOf.length != 0) {
                    int length = copyOf.length + i;
                    long[] jArr2 = ke0Var.a;
                    if (jArr2.length < length) {
                        ke0Var.a = Arrays.copyOf(jArr2, Math.max(length, (jArr2.length * 3) / 2));
                    }
                    long[] jArr3 = ke0Var.a;
                    int i2 = ke0Var.b;
                    if (i != i2) {
                        i8.M(jArr3, jArr3, copyOf.length + i, i, i2);
                    }
                    i8.M(copyOf, jArr3, i, 0, copyOf.length);
                    ke0Var.b += copyOf.length;
                }
            } else {
                v7.f("");
                throw null;
            }
        } else {
            ke0Var = new ke0();
        }
        this.f = ke0Var;
    }

    public j2(Context context) {
        this.e = 7;
        this.f = context.getApplicationContext();
    }

    public j2(float f, float f2, i7 i7Var) {
        this.e = 27;
        int b = i7Var.b();
        ms[] msVarArr = new ms[b];
        for (int i = 0; i < b; i++) {
            msVarArr[i] = new ms(f, f2, i7Var.a(i));
        }
        this.f = msVarArr;
    }

    public j2(float f, float f2) {
        this.e = 28;
        this.f = new ms(f, f2, 0.01f);
    }
}
