/*===========================================================================
*
*                            PUBLIC DOMAIN NOTICE
*               National Center for Biotechnology Information
*
*  This software/database is a "United States Government Work" under the
*  terms of the United States Copyright Act.  It was written as part of
*  the author's official duties as a United States Government employee and
*  thus cannot be copyrighted.  This software/database is freely available
*  to the public for use. The National Library of Medicine and the U.S.
*  Government have not placed any restriction on its use or reproduction.
*
*  Although all reasonable efforts have been taken to ensure the accuracy
*  and reliability of the software and data, the NLM and the U.S.
*  Government do not and cannot warrant the performance or results that
*  may be obtained by using this software or data. The NLM and the U.S.
*  Government disclaim all warranties, express or implied, including
*  warranties of performance, merchantability or fitness for any particular
*  purpose.
*
*  Please cite the author in any work or product based on this material.
*
* ===========================================================================
*
*/

#include <ngs/ncbi/NGS.hpp>

#include <ngs/itf/ErrBlock.hpp>

#include <ngs/itf/ReadCollectionItf.h>
#include "NCBI-NGS.h"

namespace ncbi
{

    /* open
     *  create an object representing a named collection of reads
     *  "spec" may be a path to an object
     *  or may be an id, accession, or URL
     */
    ReadCollection NGS :: openReadCollection ( const String & spec )
        throw ( ErrorMsg )
    {
        // call directly into ncbi-vdb library
        ngs :: ErrBlock err;
        NGS_ReadCollection_v1 * ret = NCBI_NGS_OpenReadCollection ( spec . c_str (), & err );

        // check for errors
        err . Check ();

        // create ReadCollection object
        return ReadCollection ( ( ngs :: ReadCollectionRef ) ret );
    }
}
