import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import ngs.ReadCollection;
import ngs.Alignment;
import ngs.ErrorMsg;

import gov.nih.nlm.ncbi.ngs.NGS;

//  The purpose of this suite is to verify integration of Java/JNI/C code, 
//  for which running through just one type of archive is enough. 
//  Thus these tests are not replicated for SRA and SRADB 
//  archives, unlike in the C-level test suites
public class ngs_test_CSRA1 {

// ReadCollection
    String PrimaryOnly      = "SRR1063272";
    String WithSecondary    = "SRR833251";
    String WithGroups       = "SRR822962";
    
    @Test
    public void open_success() throws ngs.ErrorMsg
    {
        ngs.ReadCollection run = NGS . openReadCollection ( PrimaryOnly );
    }
    
    @Test
    public void open_fail()
    {
        try
        {
            ngs.ReadCollection run = NGS . openReadCollection ( "SRRsomejunk" );
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    
    @Test
    public void ReadCollection_getName() throws ngs.ErrorMsg
    {
        assertEquals ( PrimaryOnly, NGS . openReadCollection ( PrimaryOnly ) . getName () );
    }
    
    @Test
    public void ReadCollection_getReadGroup() throws ngs.ErrorMsg
    {
        ngs.ReadGroup gr = NGS . openReadCollection ( PrimaryOnly ) . getReadGroup ( "C1ELY.6" );
    }
    
    @Test
    public void ReadCollection_getReadGroups() throws ngs.ErrorMsg
    {
        ngs.ReadGroupIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReadGroups ();
    }
    
    @Test
    public void ReadCollection_getReferences() throws ngs.ErrorMsg
    {
        ngs.ReferenceIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReferences ();
    }
    
    @Test
    public void ReadCollection_getReference() throws ngs.ErrorMsg
    {
        ngs.Reference ref = NGS . openReadCollection ( PrimaryOnly ) . getReference ( "supercont2.1" );
    }
    
    @Test
    public void ReadCollection_getAlignment() throws ngs.ErrorMsg
    {
        ngs.Alignment al = NGS . openReadCollection ( PrimaryOnly ) . getAlignment( PrimaryOnly + ".PA.1" );
    }
    
    @Test
    public void ReadCollection_getAlignments_Primary() throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator alIt = NGS . openReadCollection ( PrimaryOnly ) . getAlignments( Alignment . primaryAlignment );
    }
    @Test
    public void ReadCollection_getAlignments_Secondary() throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator alIt = NGS . openReadCollection ( PrimaryOnly ) . getAlignments( Alignment . secondaryAlignment );
    }
    @Test
    public void ReadCollection_getAlignments_all() throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator alIt = NGS . openReadCollection ( PrimaryOnly ) . getAlignments( Alignment . all );
    }

    @Test
    public void ReadCollection_getAlignmentCount_PrimaryOnly() throws ngs.ErrorMsg
    {
        assertEquals ( 3987701, NGS . openReadCollection ( PrimaryOnly ) . getAlignmentCount () );
    }
    @Test
    public void ReadCollection_getAlignmentCount_PrimaryOnly_Primary() throws ngs.ErrorMsg
    {
        assertEquals ( 3987701, NGS . openReadCollection ( PrimaryOnly ) . getAlignmentCount ( Alignment . primaryAlignment ) );
    }
    @Test
    public void ReadCollection_getAlignmentCount_PrimaryOnly_Secondary() throws ngs.ErrorMsg
    {
        assertEquals ( 0, NGS . openReadCollection ( PrimaryOnly ) . getAlignmentCount ( Alignment . secondaryAlignment ) );
    }
    @Test
    public void ReadCollection_getAlignmentCount_PrimaryOnly_All() throws ngs.ErrorMsg
    {
        assertEquals ( 3987701, NGS . openReadCollection ( PrimaryOnly ) . getAlignmentCount ( Alignment . all ) );
    }
    
    @Test
    public void ReadCollection_getAlignmentCount_WithSecondary() throws ngs.ErrorMsg
    {
        assertEquals ( 178, NGS . openReadCollection ( WithSecondary ) . getAlignmentCount () );
    }
    @Test
    public void ReadCollection_getAlignmentCount_WithSecondary_Primary() throws ngs.ErrorMsg
    {
        assertEquals ( 168, NGS . openReadCollection ( WithSecondary ) . getAlignmentCount ( Alignment . primaryAlignment ) );
    }
    @Test
    public void ReadCollection_getAlignmentCount_WithSecondary_Secondary() throws ngs.ErrorMsg
    {
        assertEquals ( 10, NGS . openReadCollection ( WithSecondary ) . getAlignmentCount ( Alignment . secondaryAlignment ) );
    }
    @Test
    public void ReadCollection_getAlignmentCount_WithSecondary_All() throws ngs.ErrorMsg
    {
        assertEquals ( 178, NGS . openReadCollection ( WithSecondary ) . getAlignmentCount ( Alignment . all ) );
    }
    
    @Test
    public void ReadCollection_getAlignmentRange() throws ngs.ErrorMsg
    {   // straddling primary and secondary alignments
        ngs . AlignmentIterator alIt = NGS . openReadCollection ( WithSecondary ) . getAlignmentRange ( 166, 5 );
        assertTrue ( alIt . nextAlignment () );
        assertEquals ( WithSecondary + ".PA.166", alIt . getAlignmentId () );
    }
    
    @Test
    public void ReadCollection_getRead () throws ngs.ErrorMsg
    {   
        ngs . Read read = NGS . openReadCollection ( PrimaryOnly ) . getRead ( PrimaryOnly + ".R.1" );
        assertEquals ( PrimaryOnly + ".R.1", read . getReadId () );
    }

    @Test
    public void ReadCollection_getReads () throws ngs.ErrorMsg
    {   
        ngs . ReadIterator readIt = NGS . openReadCollection ( PrimaryOnly ) . getReads( ngs . Read . all );
        assertTrue ( readIt . nextRead () );
        assertEquals ( PrimaryOnly + ".R.1", readIt . getReadId () );
    }
    
    @Test
    public void ReadCollection_getReadCount () throws ngs.ErrorMsg
    {   
        assertEquals ( 2280633, NGS . openReadCollection ( PrimaryOnly ) . getReadCount () );
    }
    
    @Test
    public void ReadCollection_getReadRange () throws ngs.ErrorMsg
    {   
        ngs . ReadIterator readIt = NGS . openReadCollection ( PrimaryOnly ) . getReadRange ( 2, 3 );
        assertTrue ( readIt . nextRead () );
        assertEquals ( PrimaryOnly + ".R.2", readIt . getReadId () );
    }

// Read 

    ngs.Read getRead ( String id ) throws ngs.ErrorMsg
    {
        ngs.ReadCollection run = NGS . openReadCollection ( PrimaryOnly );
        return run . getRead(id);
    }
    
    @Test
    public void Read_getReadCategory_full() throws ngs.ErrorMsg
    {
        assertEquals( ngs . Read . fullyAligned, getRead( PrimaryOnly + ".R.1" ) . getReadCategory() );
    }
    @Test
    public void Read_getReadCategory_partial() throws ngs.ErrorMsg
    {
        assertEquals( ngs . Read . partiallyAligned, getRead( PrimaryOnly + ".R.3" ) . getReadCategory() );
    }
    
    @Test
    public void Read_getNumFragments() throws ngs.ErrorMsg
    {
        assertEquals( 2, getRead( PrimaryOnly + ".R.1" ) . getNumFragments() );
    }

// FragmentIterator
    @Test
    public void FragmentIterator_ThrowsBeforeNext() throws ngs.ErrorMsg
    {
        try 
        {
            getRead( PrimaryOnly + ".R.1" ) . getFragmentId();
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    @Test
    public void FragmentIterator_Next() throws ngs.ErrorMsg
    {
        ngs . Read read = getRead( PrimaryOnly + ".R.1" );
        assertTrue ( read . nextFragment () );
        read . getReadCategory(); // does not throw
    }

    @Test
    public void Read_getFragmentId() throws ngs.ErrorMsg
    {
        ngs . Read read = getRead( PrimaryOnly + ".R.1" );
        assertTrue ( read . nextFragment () );
        assertEquals( PrimaryOnly + ".FR0.1", read . getFragmentId () );
    }
    
    @Test
    public void getFragmentBases() throws ngs.ErrorMsg
    {
        ngs . Read read = getRead( PrimaryOnly + ".R.1" );
        assertTrue ( read . nextFragment () );
        assertTrue ( read . nextFragment () );
        assertEquals( "GGTA", read . getFragmentBases ( 2, 4 ) );
    }
    
    @Test
    public void getFragmentQualities() throws ngs.ErrorMsg
    {
        ngs . Read read = getRead( PrimaryOnly + ".R.1" );
        assertTrue ( read . nextFragment () );
        assertTrue ( read . nextFragment () );
        assertEquals( "@DDA", read . getFragmentQualities ( 2, 4 ) );
    }

    
// Alignment
    
    ngs.Alignment getAlignment ( String id ) throws ngs.ErrorMsg
    {
        ngs.ReadCollection run = NGS . openReadCollection ( PrimaryOnly );
        return run . getAlignment(id);
    }
    ngs.Alignment getSecondaryAlignment ( String id ) throws ngs.ErrorMsg
    {
        ngs.ReadCollection run = NGS . openReadCollection ( WithSecondary );
        return run . getAlignment(id);
    }

    @Test
    public void Alignment_getAlignmentId() throws ngs.ErrorMsg
    {
        assertEquals( PrimaryOnly + ".PA.1", getAlignment ( PrimaryOnly + ".PA.1" ).getAlignmentId() );
    }
    
    @Test
    public void Alignment_getReferenceSpec() throws ngs.ErrorMsg
    {
        assertEquals( "supercont2.1", getAlignment ( PrimaryOnly + ".PA.1" ).getReferenceSpec() );
    }

    @Test
    public void Alignment_getMappingQuality() throws ngs.ErrorMsg
    {
        assertEquals( 60, getAlignment ( PrimaryOnly + ".PA.1" ).getMappingQuality() );
    }
    
    @Test
    public void Alignment_getReferenceBases() throws ngs.ErrorMsg
    {
        assertEquals( "ACTCGACATTCTGTCTTCGACCTATCTTTCTCCTCTCCCAGTCATCGCCCAGTAGAATTACCAGGCAATGAACCACGGCCTTTCATCCCAACGGCACAGCA", 
                      getAlignment ( PrimaryOnly + ".PA.1" ).getReferenceBases() );
    }
    
    @Test
    public void Alignment_getReadGroup() throws ngs.ErrorMsg
    {
        assertEquals( "C1ELY.6", getAlignment ( PrimaryOnly + ".PA.1" ).getReadGroup() );
    }
    
    @Test
    public void Alignment_getReadId() throws ngs.ErrorMsg
    {
        assertEquals( PrimaryOnly + ".R.165753", getAlignment ( PrimaryOnly + ".PA.5" ).getReadId() );
    }

    @Test
    public void Alignment_getFragmentId() throws ngs.ErrorMsg
    {
        assertEquals( PrimaryOnly + ".FA0.1", getAlignment ( PrimaryOnly + ".PA.1" ) . getFragmentId () );
    }
    
    @Test
    public void Alignment_getFragmentBases_Raw() throws ngs.ErrorMsg
    {
        assertEquals( "TGGATGCTCTGGAAAATCTGAAAAGTGGTGTTTGTAAGGTTTGCTGGCTGCCCATATACCACATGGATGATGGGGCTTTCCATTTTAATGTTGAAGGAGGA", 
                      getAlignment ( PrimaryOnly + ".PA.4" ).getFragmentBases () );
    }
    
    @Test
    public void Alignment_getFragmentQualities_Raw() throws ngs.ErrorMsg
    {
        assertEquals( "######AA>55;5(;63;;3@;A9??;6..73CDCIDA>DCB>@B=;@B?;;ADAB<DD?1*>@C9:EC?2++A3+F4EEB<E>EEIEDC2?C:;AB+==1", 
                      getAlignment ( PrimaryOnly + ".PA.4" ).getFragmentQualities () );
    }
    
    @Test
    public void Alignment_getFragmentBases_Clipped() throws ngs.ErrorMsg
    {
        assertEquals( "CTTCAACATTAAAATGGAAAGCCCCATCATCCATGTGGTATATGGGCAGCCAGCAAACCTTACAAACACCACTTTTCAGATTTTCCAGAGCATCCA", 
                      getAlignment ( PrimaryOnly + ".PA.4" ).getClippedFragmentBases () );
    }
    
    @Test
    public void Alignment_getFragmentQualities_Clipped() throws ngs.ErrorMsg
    {
        assertEquals( "#AA>55;5(;63;;3@;A9??;6..73CDCIDA>DCB>@B=;@B?;;ADAB<DD?1*>@C9:EC?2++A3+F4EEB<E>EEIEDC2?C:;AB+==1", 
                      getAlignment ( PrimaryOnly + ".PA.4" ).getClippedFragmentQualities () );
    }

    @Test
    public void Alignment_getAlignedFragmentBases() throws ngs.ErrorMsg
    {
        assertEquals( "ATATGGGTTCACTCCAACAGTGAACCATTCCAAAAGACCTTGCCTGCGTGGCCATCTCCTCACAAACCCACCATCCCGCAACATCTCAGGTATCATACCTT", 
                      getAlignment ( PrimaryOnly + ".PA.2" ).getAlignedFragmentBases () );
    }
    
    @Test
    public void Alignment_getAlignmentCategory() throws ngs.ErrorMsg
    {
        assertEquals( ngs . Alignment . primaryAlignment, getAlignment ( PrimaryOnly + ".PA.4" ).getAlignmentCategory () );
    }
    
    @Test
    public void Alignment_getAlignmentPosition() throws ngs.ErrorMsg
    {
        assertEquals( 85, getAlignment ( PrimaryOnly + ".PA.1" ).getAlignmentPosition () );
    }
    
    @Test
    public void Alignment_getAlignmentLength() throws ngs.ErrorMsg
    {
        assertEquals( 101, getAlignment ( PrimaryOnly + ".PA.1" ).getAlignmentLength () );
    }
    
    @Test
    public void Alignment_getIsReversedOrientation_False() throws ngs.ErrorMsg
    {
        assertFalse( getAlignment ( PrimaryOnly + ".PA.1" ).getIsReversedOrientation () );
    }
    @Test
    public void Alignment_getIsReversedOrientation_True() throws ngs.ErrorMsg
    {
        assertTrue( getAlignment ( PrimaryOnly + ".PA.2" ).getIsReversedOrientation () );
    }
    
    @Test
    public void Alignment_getSoftClip_None() throws ngs.ErrorMsg
    {
        ngs . Alignment al = getAlignment ( PrimaryOnly + ".PA.1" );
        assertEquals( 0, al . getSoftClip ( ngs . Alignment . clipLeft ) );
        assertEquals( 0, al . getSoftClip ( ngs . Alignment . clipRight ) );
    }
    @Test
    public void Alignment_getSoftClip_Left() throws ngs.ErrorMsg
    {
        ngs . Alignment al = getAlignment ( PrimaryOnly + ".PA.4" );
        assertEquals( 5, al . getSoftClip ( ngs . Alignment . clipLeft ) );
        assertEquals( 0, al . getSoftClip ( ngs . Alignment . clipRight ) );
    }
    @Test
    public void Alignment_getSoftClip_Right() throws ngs.ErrorMsg
    {
        ngs . Alignment al = getAlignment ( PrimaryOnly + ".PA.10" );
        assertEquals( 0,  al . getSoftClip ( ngs . Alignment . clipLeft ) );
        assertEquals( 13, al . getSoftClip ( ngs . Alignment . clipRight ) );
    }
    
    @Test
    public void Alignment_getTemplateLength() throws ngs.ErrorMsg
    {
        assertEquals( 201, getAlignment ( PrimaryOnly + ".PA.1" ).getTemplateLength () );
    }
    
    @Test
    public void Alignment_getShortCigar_Unclipped() throws ngs.ErrorMsg
    {
        assertEquals( "5S96M", getAlignment ( PrimaryOnly + ".PA.4" ).getShortCigar ( false ) );
    }
    @Test
    public void Alignment_getShortCigar_Clipped() throws ngs.ErrorMsg
    {
        assertEquals( "96M", getAlignment ( PrimaryOnly + ".PA.4" ).getShortCigar ( true ) );
    }

    @Test
    public void Alignment_getLongCigar_Unclipped() throws ngs.ErrorMsg
    {
        assertEquals( "5S1X8=1X39=1X46=", getAlignment ( PrimaryOnly + ".PA.4" ).getLongCigar ( false ) );
    }
    @Test
    public void Alignment_getLongCigar_Clipped() throws ngs.ErrorMsg
    {
        assertEquals( "1X8=1X39=1X46=", getAlignment ( PrimaryOnly + ".PA.4" ).getLongCigar ( true ) );
    }
    
    @Test
    public void Alignment_hasMate_Primary_No() throws ngs.ErrorMsg
    {
        assertFalse( getAlignment ( PrimaryOnly + ".PA.99" ) . hasMate ( ) );
    }
    @Test
    public void Alignment_hasMate_Primary_Yes() throws ngs.ErrorMsg
    {
        assertTrue( getAlignment ( PrimaryOnly + ".PA.1" ) . hasMate ( ) );
    }
    @Test
    public void Alignment_hasMate_Secondary() throws ngs.ErrorMsg
    {
        assertFalse( getSecondaryAlignment ( WithSecondary + ".SA.169" ) . hasMate ( ) );
    }

    @Test
    public void Alignment_getMateAlignmentId() throws ngs.ErrorMsg
    {
        assertEquals ( PrimaryOnly + ".PA.2", getAlignment ( PrimaryOnly + ".PA.1" ) . getMateAlignmentId ( ) );
    }
    @Test
    public void Alignment_getMateAlignmentId_Missing() throws ngs.ErrorMsg
    {
        try
        {
            getAlignment ( PrimaryOnly + ".PA.99" ) . getMateAlignmentId ( );
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    @Test
    public void Alignment_getMateAlignmentId_SecondaryThrows() throws ngs.ErrorMsg
    {
        try
        {
            getSecondaryAlignment ( WithSecondary + ".SA.172" ) . getMateAlignmentId ( );
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    
    @Test
    public void Alignment_getMateAlignment() throws ngs.ErrorMsg
    {
        assertEquals ( PrimaryOnly + ".PA.2",  getAlignment ( PrimaryOnly + ".PA.1" ) . getMateAlignment () . getAlignmentId () );
    }
    @Test
    public void Alignment_getMateAlignment_Missing() throws ngs.ErrorMsg
    {
        try 
        {
            getAlignment ( PrimaryOnly + ".PA.99" ) . getMateAlignment ();
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    @Test
    public void Alignment_getMateAlignment_SecondaryThrows() throws ngs.ErrorMsg
    {
        try 
        {
            getSecondaryAlignment ( WithSecondary +".SA.172" ) . getMateAlignment ();
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    
    @Test
    public void Alignment_getMateReferenceSpec() throws ngs.ErrorMsg
    {
        assertEquals ( "supercont2.1",  getAlignment ( PrimaryOnly + ".PA.1" ) . getMateReferenceSpec () );
    }
    
    @Test
    public void Alignment_getMateIsReversedOrientation_Yes() throws ngs.ErrorMsg
    {
        assertTrue( getAlignment ( PrimaryOnly + ".PA.1" ) . getMateIsReversedOrientation () );
    }
    @Test
    public void Alignment_getMateIsReversedOrientation_No() throws ngs.ErrorMsg
    {
        assertFalse( getAlignment ( PrimaryOnly + ".PA.2" ) . getMateIsReversedOrientation () );
    }
    
// Reference
    public ngs . Reference getReference() throws ngs.ErrorMsg
    {
        return NGS . openReadCollection ( PrimaryOnly ) . getReference ( "supercont2.1" );
    }

    @Test
    public void Reference_getCommonName () throws ngs.ErrorMsg
    {
        assertEquals ( "supercont2.1", getReference () . getCommonName () );
    }
    
    @Test
    public void Reference_getCanonicalName () throws ngs.ErrorMsg
    {
        assertEquals ( "NC_000007.13", NGS . openReadCollection ( "SRR821492" ) . getReference ( "chr7" ) . getCanonicalName () );
    }
    
    @Test
    public void Reference_getIsCircular_No() throws ngs.ErrorMsg
    {
        assertFalse( getReference () . getIsCircular () );
    }
    @Test
    public void Reference_getIsCircular_Yes() throws ngs.ErrorMsg
    {
        assertTrue( NGS . openReadCollection ( "SRR821492" ) . getReference ( "chrM" ) . getIsCircular () );
    }
    
    @Test
    public void Reference_getLength() throws ngs.ErrorMsg
    {
        assertEquals ( 2291499l, getReference () . getLength() );
    }
    
    @Test
    public void Reference_getReferenceBases() throws ngs.ErrorMsg
    {
        assertEquals ( "ATCTG", getReference () . getReferenceBases ( 2291499l - 5 ) );
    }
    @Test
    public void Reference_getReferenceBases_Length() throws ngs.ErrorMsg
    {
        assertEquals ( "GCGCTATGAC", getReference () . getReferenceBases ( 9000, 10 ) );
    }
    
    @Test
    public void Reference_getReferenceChunk() throws ngs.ErrorMsg
    {
        assertEquals ( "CTAGG", getReference () . getReferenceChunk ( 5000 - 5, 5 ) );
    }
    @Test
    public void Reference_getReferenceChunk_Length () throws ngs.ErrorMsg
    {
        assertEquals ( "GCGCTATGAC", getReference () . getReferenceChunk ( 9000, 10 ) );
    }
    
    @Test
    public void Reference_getAlignment() throws ngs.ErrorMsg
    {
        assertEquals ( PrimaryOnly + ".PA.1", getReference () . getAlignment ( PrimaryOnly + ".PA.1" ) . getAlignmentId () );
    }
    
//TODO: getPileups
//TODO: getPileupSlice
//TODO: getPileupSlice_Filtered

// ReferenceIterator

    @Test
    public void ReferenceIterator_ThrowBeforeNext() throws ngs.ErrorMsg
    {
        ngs.ReferenceIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReferences ();
        try 
        {
            it . getCommonName ();
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    
    @Test
    public void ReferenceIterator_Next() throws ngs.ErrorMsg
    {
        ngs.ReferenceIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReferences ();
        assertTrue ( it . nextReference () );
        assertEquals ( "supercont2.1", it . getCommonName () );
    }
    
// AlignmentIterator from Reference (ReferenceWindow)
    @Test
    public void ReferenceWindow () throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator it = NGS . openReadCollection ( WithSecondary ) 
                                        . getReference ( "gi|169794206|ref|NC_010410.1|" )
                                        . getAlignments ( ngs . Alignment . all ); 
        assertTrue ( it . nextAlignment () );  
    
        // the first 2 secondary alignments' locations on the list: #34, #61
        long count = 1;
        while ( it . nextAlignment() )
        {
            if ( it . getAlignmentCategory()  == ngs . Alignment . secondaryAlignment )
                break;
            ++count;
        }
        assertEquals ( 34, count);    
        while ( it . nextAlignment() )
        {
            if ( it . getAlignmentCategory()  == ngs . Alignment . secondaryAlignment )
                break;
            ++count;
        }
        assertEquals ( 61, count);    
    }
    
    @Test
    public void ReferenceWindow_Slice () throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator it = NGS . openReadCollection ( WithSecondary ) 
                                        . getReference ( "gi|169794206|ref|NC_010410.1|" )
                                        . getAlignmentSlice ( 516000, 100000 ); 
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.33", it. getAlignmentId () );
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.34", it. getAlignmentId () );
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".SA.169", it. getAlignmentId () ); //secondary
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.35", it. getAlignmentId () );
        assertFalse ( it . nextAlignment () );  
    }
   
    @Test
    public void ReferenceWindow_Slice_Filtered () throws ngs.ErrorMsg
    {
        ngs.AlignmentIterator it = NGS . openReadCollection ( WithSecondary ) 
                                        . getReference ( "gi|169794206|ref|NC_010410.1|" )
                                        . getAlignmentSlice ( 516000, 100000, ngs . Alignment . primaryAlignment ); 
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.33", it. getAlignmentId () );
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.34", it. getAlignmentId () );
        assertTrue ( it . nextAlignment () );  
        assertEquals ( WithSecondary + ".PA.35", it. getAlignmentId () ); // no secondary
        assertFalse ( it . nextAlignment () );  
    }
    
    // ReadGroup 
    @Test
    public void ReadGroup_getName () throws ngs.ErrorMsg
    {
        ngs.ReadGroup gr = NGS . openReadCollection ( PrimaryOnly ) . getReadGroup ( "C1ELY.6" );
        assertEquals( "C1ELY.6", gr . getName () );
    }
    @Test
    public void ReadGroup_getStatistics() throws ngs.ErrorMsg
    {
        ngs.ReadGroup gr = NGS . openReadCollection ( WithGroups ) . getReadGroup ( "GS57510-FS3-L03" );

        ngs . Statistics stats = gr . getStatistics ();
    
        assertEquals ( 34164461870L, stats . getAsU64 ( "BASE_COUNT" ) );
        assertEquals ( 34164461870L, stats . getAsU64 ( "BIO_BASE_COUNT" ) );
        assertEquals ( 488063741L,   stats . getAsU64 ( "SPOT_COUNT" ) );
        assertEquals ( 5368875807L,  stats . getAsU64 ( "SPOT_MAX" ) );
        assertEquals ( 4880812067L,  stats . getAsU64 ( "SPOT_MIN" ) );        
    }

/* ReadGroup no longer supports Reads
    @Test
    public void ReadGroup_getRead () throws ngs.ErrorMsg
    {
        ngs.ReadGroup gr = NGS . openReadCollection ( PrimaryOnly ) . getReadGroup ( "C1ELY.6" );
        ngs.Read r = gr . getRead ( PrimaryOnly + ".R.1" );
        assertEquals ( "C1ELY.6", r . getReadGroup () );
    }
    
    @Test
    public void ReadGroup_getReads () throws ngs.ErrorMsg
    {
        ngs.ReadGroup gr = NGS . openReadCollection ( PrimaryOnly ) . getReadGroup ( "C1ELY.6" );
        ngs.ReadIterator r = gr . getReads ( ngs . Read . partiallyAligned );
    }
*/
    
    // ReadGroupIterator
    @Test
    public void ReadGroupIterator_ThrowBeforeNext() throws ngs.ErrorMsg
    {
        ngs.ReadGroupIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReadGroups ();
        try 
        {
            it . getName ();
            fail();
        }
        catch ( ngs.ErrorMsg e ) {}        
    }
    
    @Test
    public void ReadGroupIterator_Next() throws ngs.ErrorMsg
    {
        ngs.ReadGroupIterator it = NGS . openReadCollection ( PrimaryOnly ) . getReadGroups ();
        assertTrue ( it . nextReadGroup () );
        String name = it . getName ();
/*
        ngs.ReadIterator r = it . getReads ( ngs . Read . all );
        assertTrue ( r . nextRead () );
        assertEquals ( name, r . getReadGroup () );
*/
    }    
}
